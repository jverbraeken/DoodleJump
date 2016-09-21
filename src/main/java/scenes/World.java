package scenes;

import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.blocks.platform.IPlatform;
import objects.buttons.IButton;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rendering.IDrawable;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;
import system.IUpdatable;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class World implements IScene {

    /**
     * How much the doodle is affected by gravity.
     */
    public static final double gravityAcceleration = .5;
    // TODO: Add JavaDoc
    private final static double SCOREMULTIPLIER = 0.15;
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
     * The score for the world.
     */
    private double score;
    /**
     * The highest (and thus latest) created block.
     */
    private IBlock topBlock;

    /* package */ World(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
        Game.setAlive(true);

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        topBlock = blockFactory.createStartBlock();
        blocks.add(topBlock);

        for (int i = 1; i < 3; i++) {
            topBlock = blockFactory.createBlock(topBlock.getTopJumpable());
            blocks.add(topBlock);
        }

        background = serviceLocator.getSpriteFactory().getBackground();

        scorebar = new Scorebar();

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
        this.doodle.setVerticalSpeed(-9);

        serviceLocator.getAudioManager().playStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
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
        serviceLocator.getRenderer().drawSprite(this.background, 0, 0);

        for (IBlock e : blocks) {
            e.render();
        }

        this.doodle.render();

        scorebar.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double delta) {
        checkDoodleCollissions();
        updateObjects(delta);
        applySpeed(delta);
        cleanUp();

        // TODO: check if doodle is alive

        newBlocks();
        Game.setAlive(doodle.getYPos() < Game.HEIGHT);
    }

    /**
     * TODO: Add JavaDoc
     */
    private void checkDoodleCollissions() {
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
     * TODO: Add JavaDoc
     */
    private void applySpeed(double delta) {
        if (doodle.getVSpeed() < 0d && doodle.getYPos() < .5d * Game.HEIGHT - doodle.getHitBox()[AGameObject.HITBOX_BOTTOM]) {
            for (IBlock e : blocks) {
                e.addYPos(-doodle.getVSpeed());
                score -= doodle.getVSpeed() * SCOREMULTIPLIER;
            }
        } else {
            doodle.addYPos(doodle.getVSpeed());
        }
    }

    /**
     * TODO: Add JavaDoc
     */
    private void updateObjects(double delta) {
        for (IUpdatable e : blocks) {
            e.update(delta);
        }

        doodle.update(delta);
    }

    /**
     * Checks for all the Blocks if they are under over the height
     * of the screen, if that's the case, delete that Block.
     */
    private void cleanUp() {
        HashSet<IBlock> toRemove = new HashSet<>();
        for (IBlock e : blocks) {
            if (e.getTopJumpable().getYPos() > Game.HEIGHT) {
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
            //TODO: implements New Block
            blocks.add(topBlock);
        }
    }

    /**
     * IMMUTABLE
     * <p>
     * The bar on top of the screen displaying the score and pause button
     */
    private final class Scorebar {
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
            scaling = (double) Game.WIDTH / (double) scoreBarSprite.getWidth();
            scoreBarHeight = (int) (scaling * scoreBarSprite.getHeight());

            ISprite[] digitSprites = new ISprite[10];
            for (int i = 0; i < 10; i++) {
                digitSprites[i] = serviceLocator.getSpriteFactory().getDigitSprite(i);
            }
            int scoreX = (int) (digitSprites[2].getWidth() * scaling);
            int scoreY = (int) (scaling * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2);
            scoreText = new ScoreText(scoreX, scoreY, scaling, digitSprites);

            ISprite pauseSprite = serviceLocator.getSpriteFactory().getPauseButtonSprite();
            int pauseX = (int) (Game.WIDTH - pauseSprite.getWidth() * scaling - PAUSEOFFSET);
            int pauseY = (int) (((double) Game.WIDTH / (double) scoreBarSprite.getWidth()) * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2 - pauseSprite.getHeight() / 2);
            pauseButton = new PauseButton(pauseX, pauseY, scaling, pauseSprite);

            serviceLocator.getInputManager().addObserver(pauseButton);
        }

        private void render() {
            serviceLocator.getRenderer().drawSprite(scoreBarSprite, 0, 0, Game.WIDTH, scoreBarHeight);
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
            }

            @Override
            public void render() {
                serviceLocator.getRenderer().drawSprite(sprite, x, y, width, height);
            }

            @Override
            public void mouseClicked(int mouseX, int mouseY) {
                if (mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height) {
                    logger.info("Pause button was clicked!");
                    Game.setPaused(true);
                }
            }
        }

        private class ScoreText implements IDrawable {
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
                int roundedScore = (int) score;
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
                    serviceLocator.getRenderer().drawSprite(sprite, pos, digitData[digit * 3], digitData[digit * 3 + 1], digitData[digit * 3 + 2]);
                    pos += digitData[digit * 3 + 1] + 1;
                }
            }
        }
    }

}
