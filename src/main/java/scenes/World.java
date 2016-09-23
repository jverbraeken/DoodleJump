package scenes;

import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.buttons.IButton;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.sprites.ISprite;
import system.Game;
import system.IRenderable;
import system.IServiceLocator;
import system.IUpdatable;

import java.util.*;

public class World implements IScene {

    /**
     * How much the doodle is affected by gravity.
     */
    public static final double gravityAcceleration = .5;
    // TODO: Add JavaDoc / shouldn't be public
    public final static double SCOREMULTIPLIER = 0.15;
    private final static int PAUSEOFFSET = 38;
    /**
     * The maximum number of blocks available at a time.
     */
    private static final int MAXBLOCKS = 3;
    private final Logger logger = LoggerFactory.getLogger(World.class);
    private final IServiceLocator serviceLocator;
    /**
     * Set of all object (excluding Doodle) in the world.
     */
    private final Set<IBlock> blocks = new HashSet<>();
    /**
     * The background of the world.
     */
    private final ISprite background;
    /**
     * The Doodle for the world.
     */
    private final IDoodle doodle;
    /**
     * The scorebar for the world.
     */
    private final Scorebar scorebar;
    /**
     * The highest (and thus latest) created block.
     */
    private IBlock topBlock;

    /**
     * <p>Drawables consists of 3 sets, although this can be easily changed. The first set contains the
     * {@link IRenderable renderables} that will be drawn first (eg platforms), the second set contains the
     * {@link IRenderable renderables} that will be drawn secondly (eg doodles) and the third set contains the
     * {@link IRenderable renderables} that will be drawn at last (eg HUD elements).</p>
     *
     * <p>The reason a list is used instead of an array is because we need to use a weak set. The only
     * way to make it (in Java) is by using Collections.newSetFromMap that creates the set for us (and
     * prohibits creating an array by doing that).</p>
     */
    private List<Set<IRenderable>> drawables = new ArrayList<>();
    private Set<IUpdatable> updatables = Collections.newSetFromMap(new WeakHashMap<>());

    /* package */ World(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
        Game.setAlive(true);

        for (int i = 0; i < 3; i++) {
            drawables.add(Collections.newSetFromMap(new WeakHashMap<>()));
        }

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        this.topBlock = blockFactory.createStartBlock();
        this.blocks.add(this.topBlock);
        this.drawables.get(0).add(this.topBlock);
        this.updatables.add(this.topBlock);

        for (int i = 1; i < 3; i++) {
            this.topBlock = blockFactory.createBlock(this.topBlock.getTopJumpable());
            this.blocks.add(this.topBlock);
            this.drawables.get(0).add(this.topBlock);
            this.updatables.add(this.topBlock);
        }

        this.background = serviceLocator.getSpriteFactory().getBackground();

        this.scorebar = new Scorebar();
        this.drawables.get(2).add(this.scorebar);

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
        this.doodle.setVerticalSpeed(-9);
        this.drawables.get(1).add(this.doodle);
        this.updatables.add(this.doodle);

        this.serviceLocator.getAudioManager().playStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.serviceLocator.getRenderer().getCamera().setYPos(serviceLocator.getConstants().getGameHeight() / 2d);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        //TODO maybe we should make a Background class?
        serviceLocator.getRenderer().drawSpriteHUD(this.background, 0, 0);

        for (Set<IRenderable> set : drawables) {
            for (IRenderable e : set) {
                e.render();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double delta) {
        updateObjects(delta);
        checkCollisions();
        cleanUp();
        newBlocks();
    }

    /**
     * TODO: Add JavaDoc
     */
    private void updateObjects(double delta) {
        for (IUpdatable e : updatables) {
            e.update(delta);
        }
    }

    /**
     * TODO: Add JavaDoc
     */
    private void checkCollisions() {
        if (this.doodle.getVSpeed() > 0) {
            for (IBlock block : blocks) {
                //TODO check for the collision
                //if (this.doodle.checkCollission(block)) {
                Set<IGameObject> elements = block.getElements();
                for (IGameObject element : elements) {
                    if (this.doodle.checkCollission(element)) {
                        if (this.doodle.getYPos() + this.doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] * this.doodle.getLegsHeight() < element.getYPos()) {
                            element.collidesWith(this.doodle);
                        }
                    }
                }
                //}
            }
        }
    }

    /**
     * Checks for all the Blocks if they are under over the height
     * of the screen, if that's the case, delete that Block.
     */
    private void cleanUp() {
        HashSet<IBlock> toRemove = new HashSet<>();
        for (IBlock e : blocks) {
            if (e.getTopJumpable().getYPos() > serviceLocator.getRenderer().getCamera().getYPos() + serviceLocator.getConstants().getGameHeight()) {
                toRemove.add(e);
            }
        }

        for (IBlock e : toRemove) {
            blocks.remove(e);
        }
    }

    /**
     * TODO: Add JavaDoc
     */
    private void newBlocks() {
        if (blocks.size() < MAXBLOCKS) {
            IJumpable topPlatform = topBlock.getTopJumpable();
            topBlock = serviceLocator.getBlockFactory().createBlock(topPlatform);
            blocks.add(topBlock);
            drawables.get(0).add(topBlock);
            updatables.add(topBlock);
        }
    }

    /**
     * IMMUTABLE
     * <p>
     * The bar on top of the screen displaying the score and pause button
     */
    private final class Scorebar implements IRenderable {
        /**
         * The transparant and black border at the bottom of the scoreBar that is not take into account when
         * drawing scoreBar content.
         */
        private static final int SCOREBARDEADZONE = 28;
        private final double scaling;
        private final ISprite scoreBarSprite;
        private final int scoreBarHeight;
        private final PauseButton pauseButton;
        private final ScoreText scoreText;
        private final Logger logger = LoggerFactory.getLogger(Scorebar.class);

        private Scorebar() {
            scoreBarSprite = serviceLocator.getSpriteFactory().getScorebarSprite();
            scaling = (double) serviceLocator.getConstants().getGameWidth() / (double) scoreBarSprite.getWidth();
            scoreBarHeight = (int) (scaling * scoreBarSprite.getHeight());

            ISprite[] digitSprites = new ISprite[10];
            for (int i = 0; i < 10; i++) {
                digitSprites[i] = serviceLocator.getSpriteFactory().getDigitSprite(i);
            }
            int scoreX = (int) (digitSprites[2].getWidth() * scaling);
            int scoreY = (int) (scaling * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2);
            scoreText = new ScoreText(scoreX, scoreY, scaling, digitSprites);

            ISprite pauseSprite = serviceLocator.getSpriteFactory().getPauseButtonSprite();
            int pauseX = (int) (serviceLocator.getConstants().getGameWidth() - pauseSprite.getWidth() * scaling - PAUSEOFFSET);
            int pauseY = (int) (((double) serviceLocator.getConstants().getGameWidth() / (double) scoreBarSprite.getWidth()) * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2 - pauseSprite.getHeight() / 2);
            pauseButton = new PauseButton(pauseX, pauseY, scaling, pauseSprite);
        }

        /** {@inheritDoc} */
        @Override
        public void render() {
            serviceLocator.getRenderer().drawSpriteHUD(scoreBarSprite, 0, 0, serviceLocator.getConstants().getGameWidth(), scoreBarHeight);
            scoreText.render();
            pauseButton.render();
        }

        private class PauseButton implements IButton {
            private final int x, y, width, height;
            private final ISprite sprite;

            private PauseButton(int x, int y, double scaling, ISprite sprite) {
                this.x = x;
                this.y = y;
                this.width = (int) (sprite.getWidth() * scaling);
                this.height = (int) (sprite.getHeight() * scaling);
                this.sprite = sprite;
                serviceLocator.getInputManager().addObserver(this);
            }

            @Override
            public void render() {
                serviceLocator.getRenderer().drawSpriteHUD(sprite, x, y, width, height);
            }

            @Override
            public void mouseClicked(int mouseX, int mouseY) {
                if (mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height) {
                    logger.info("Pause button was clicked!");
                    Game.setPaused(true);
                }
            }
        }

        private class ScoreText implements IRenderable {
            private final int x;
            private final ISprite[] digitSprites;
            /**
             * We use an array so that we get very good cache prediction and a 4 times smaller size than width
             * seperate (integer) variables.
             * <br>
             * [top-Y, width, height] for each of the 10 entries -> total length = 30
             */
            private final byte[] digitData;

            private ScoreText(int x, int yCenter, double scaling, ISprite[] digitSprites) {
                assert digitSprites.length == 10;
                this.x = x;
                digitData = new byte[30];
                for (int i = 0; i < 10; i++) {
                    digitData[i * 3] = (byte) (yCenter - digitSprites[i].getHeight() / 2);
                    digitData[i * 3 + 1] = (byte) (digitSprites[i].getWidth() * scaling);
                    digitData[i * 3 + 2] = (byte) (digitSprites[i].getHeight() * scaling);
                }
                this.digitSprites = digitSprites;
            }

            @Override
            public void render() {
                assert doodle.getScore() >= 0;
                int roundedScore = (int) doodle.getScore();
                int digit;
                Stack<Integer> scoreDigits = new Stack<>();
                while (roundedScore != 0) {
                    digit = roundedScore % 10;
                    roundedScore = roundedScore / 10;
                    scoreDigits.push(digit);
                }

                int pos = x;
                ISprite sprite;
                while (!scoreDigits.isEmpty()) {
                    digit = scoreDigits.pop();
                    sprite = digitSprites[digit];
                    serviceLocator.getRenderer().drawSpriteHUD(sprite, pos, digitData[digit * 3], digitData[digit * 3 + 1], digitData[digit * 3 + 2]);
                    pos += digitData[digit * 3 + 1] + 1;
                }
            }
        }
    }

}
