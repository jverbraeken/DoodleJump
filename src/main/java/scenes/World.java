package scenes;

import buttons.IButton;
import logging.ILogger;
import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import resources.sprites.ISprite;
import system.Game;
import system.IRenderable;
import system.IServiceLocator;
import system.IUpdatable;

import java.util.*;

/**
 * This class describes the scene in which the actual standard game is played.
 */
public class World implements IScene {

    // TODO: Add JavaDoc
    private final static double SCOREMULTIPLIER = 0.15;
    private final static int PAUSEOFFSET = 38;
    /**
     * The logger for the World class.
     */
    private final ILogger LOGGER;
    /**
     * How much the doodle is affected by gravity.
     */
    public static final double GRAVITY_ACCELERATION = .5;
    /**
     * Multiplier to achieve a realistic score increase.
     */
    private final double scoremultiplier = 0.15;
    /**
     * Offset of the pausebutton.
     */
    private final int pauseoffset = 38;
    /**
     * Used to access all services.
     */
    private final IServiceLocator sL;
    /**
     * The amount of blocks kept in a buffer.
     */
    private final int blockBuffer = 4;
    /**
     * The vertical starting speed.
     */
    private final double startspeed = -9;
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
     * The Digitoffsetmultiplier needed for the scoretext.
     */
    private static final int DIGITMP = 3;

    /**
     * The current number system.
     * Standard decimal system.
     */
    private static final int NUMBERSYSTEM = 10;
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

    /* package */ World(final IServiceLocator sL) {
        assert sL != null;
        this.sL = sL;
        LOGGER = sL.getLoggerFactory().createLogger(World.class);
        Game.setAlive(true);

        for (int i = 0; i < 3; i++) {
            drawables.add(Collections.newSetFromMap(new WeakHashMap<>()));
        }

        IBlockFactory blockFactory = sL.getBlockFactory();
        this.topBlock = blockFactory.createStartBlock();
        this.blocks.add(this.topBlock);
        this.drawables.get(0).add(this.topBlock);
        this.updatables.add(this.topBlock);

        for (int i = 1; i < blockBuffer; i++) {
            this.topBlock = blockFactory.createBlock(this.topBlock.getTopJumpable());
            this.blocks.add(this.topBlock);
            this.drawables.get(0).add(this.topBlock);
            this.updatables.add(this.topBlock);
        }

        this.background = sL.getSpriteFactory().getBackground();

        this.scorebar = new Scorebar();
        this.drawables.get(2).add(this.scorebar);

        IDoodleFactory doodleFactory = sL.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
        this.doodle.setVerticalSpeed(-9);
        this.drawables.get(1).add(this.doodle);
        this.updatables.add(this.doodle);

        this.sL.getAudioManager().playStart();

        LOGGER.log("Level started");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        this.sL.getRenderer().getCamera().setYPos(sL.getConstants().getGameHeight() / 2d);
        LOGGER.log("World has been started");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        LOGGER.log("World has been stopped");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        //TODO maybe we should make a Background class?
        sL.getRenderer().drawSpriteHUD(this.background, 0, 0);

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
    public void update(final double delta) {
        updateObjects(delta);
        checkCollisions();
        cleanUp();
        newBlocks();
    }

    /**
     * Update the vertical speed.
     */
    private void updateObjects(final double delta) {
        for (IUpdatable e : updatables) {
            e.update(delta);
        }
    }

    /**
     * Apply the current speed to all objects.
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
            }
        }
    }

    /**
     * Checks for all the Blocks if they are under over the height of the screen.
     * If that's the case, delete that Block.
     */
    private void cleanUp() {
        HashSet<IBlock> toRemove = new HashSet<>();
        for (IBlock e : blocks) {
            if (e.getTopJumpable().getYPos() > sL.getRenderer().getCamera().getYPos() + sL.getConstants().getGameHeight()) {
                toRemove.add(e);
            }
        }

        for (IBlock e : toRemove) {
            blocks.remove(e);
        }
    }

    /**
     * Generate new blocks if there are under 3 present.
     */
    private void newBlocks() {
        if (blocks.size() < blockBuffer) {
            IJumpable topPlatform = topBlock.getTopJumpable();
            topBlock = sL.getBlockFactory().createBlock(topPlatform);
            blocks.add(topBlock);
            drawables.get(0).add(topBlock);
            updatables.add(topBlock);
        }
    }

    /**
     * IMMUTABLE.
     * <p>
     * The bar on top of the screen displaying the score and pause button
     */
    private final class Scorebar implements IRenderable {
        /**
         * The transparant and black border at the bottom of the scoreBar that is not take into account when
         * drawing scoreBar content.
         */
        private static final int SCOREBARDEADZONE = 28;
        /**
         * The scaling of the scorebar.
         */
        private final double scaling;
        /**
         * The sprite of the score bar.
         */
        private final ISprite scoreBarSprite;
        /**
         * The height of the score bar.
         */
        private final int scoreBarHeight;
        /**
         * The pause button.
         */
        private final PauseButton pauseButton;
        /**
         * The text display of the current score.
         */
        private final ScoreText scoreText;
        /**
         * The logger is used to keep track of all the actions performed in the game.
         */
        private final ILogger logger = sL.getLoggerFactory().createLogger(Scorebar.class);

        /**
         * Create a new scorebar.
         */
        private Scorebar() {
            scoreBarSprite = sL.getSpriteFactory().getScorebarSprite();
            scaling = (double) sL.getConstants().getGameWidth() / (double) scoreBarSprite.getWidth();
            scoreBarHeight = (int) (scaling * scoreBarSprite.getHeight());

            ISprite[] digitSprites = new ISprite[NUMBERSYSTEM];
            for (int i = 0; i < NUMBERSYSTEM; i++) {
                digitSprites[i] = sL.getSpriteFactory().getDigitSprite(i);
            }
            int scoreX = (int) (digitSprites[2].getWidth() * scaling);
            int scoreY = (int) (scaling * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2);
            scoreText = new ScoreText(scoreX, scoreY, scaling, digitSprites);

            ISprite pauseSprite = sL.getSpriteFactory().getPauseButtonSprite();
            int pauseX = (int) (sL.getConstants().getGameWidth() - pauseSprite.getWidth() * scaling - PAUSEOFFSET);
            int pauseY = (int) (((double) sL.getConstants().getGameWidth() / (double) scoreBarSprite.getWidth()) * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2 - pauseSprite.getHeight() / 2);
            pauseButton = new PauseButton(pauseX, pauseY, scaling, pauseSprite);
        }

        /** {@inheritDoc} */
        @Override
        public void render() {
            sL.getRenderer().drawSpriteHUD(scoreBarSprite, 0, 0, sL.getConstants().getGameWidth(), scoreBarHeight);
            scoreText.render();
            pauseButton.render();
        }

        /**
         * This class focuses on the implementation of the pause button.
         */
        private final class PauseButton implements IButton {
            /**
             * The position and size of the pause button.
             */
            private final int x, y, width, height;
            /**
             * The sprite of the pause button.
             */
            private final ISprite sprite;

            /**
             * Construct the pause button.
             * @param xx the x position of the pause button.
             * @param yy the y position of the pause button.
             * @param sc the scale of the button.
             * @param sp the sprite of the button.
             */
            private PauseButton(final int xx, final int yy, final double sc, final ISprite sp) {
                this.x = xx;
                this.y = yy;
                this.width = (int) (sp.getWidth() * sc);
                this.height = (int) (sp.getHeight() * sc);
                this.sprite = sp;
                sL.getInputManager().addObserver(this);
            }

            /**
             * Render the object.
             */
            @Override
            public void render() {
                sL.getRenderer().drawSpriteHUD(sprite, x, y, width, height);
            }

            /**
             * Interact when the mouse has been clicked.
             * @param mouseX the x position of the mouse.
             * @param mouseY the y position of the mouse.
             */
            @Override
            public void mouseClicked(final int mouseX, final int mouseY) {
                if (mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height) {
                    logger.info("Button clicked: \"pause\"");
                    Game.setPaused(true);
                }
            }
        }

        /**
         * This private subclass creates the text to display the curent score.
         */
        private final class ScoreText implements IRenderable {
            /**
             * The x position of the Score.
             */
            private final int x;
            /**
             * The sprites of the score digits.
             */
            private final ISprite[] digitSprites;
            /**
             * We use an array so that we get very good cache prediction and a 4 times smaller size than width
             * seperate (integer) variables.
             * <br>
             * [top-Y, width, height] for each of the 10 entries -> total length = 30
             */
            private final byte[] digitData;

            /**
             * Create the score texts.
             * @param xPos x position.
             * @param yCenter center y of the score text.
             * @param s the scale.
             * @param dS the digit sprites.
             */
            private ScoreText(final int xPos, final int yCenter, final double s, final ISprite[] dS) {
                assert dS.length == NUMBERSYSTEM;
                this.x = xPos;
                digitData = new byte[DIGITMP * NUMBERSYSTEM];
                for (int i = 0; i < NUMBERSYSTEM; i++) {
                    digitData[i * DIGITMP] = (byte) (yCenter - dS[i].getHeight() / 2);
                    digitData[i * DIGITMP + 1] = (byte) (dS[i].getWidth() * s);
                    digitData[i * DIGITMP + 2] = (byte) (dS[i].getHeight() * s);
                }
                this.digitSprites = dS;
            }

            /**
             * Render the score.
             */
            @Override
            public void render() {
                assert doodle.getScore() >= 0;
                int roundedScore = (int) doodle.getScore();
                int digit;
                Stack<Integer> scoreDigits = new Stack<>();
                while (roundedScore != 0) {
                    digit = roundedScore % NUMBERSYSTEM;
                    roundedScore = roundedScore / NUMBERSYSTEM;
                    scoreDigits.push(digit);
                }

                int pos = x;
                ISprite sprite;
                while (!scoreDigits.isEmpty()) {
                    digit = scoreDigits.pop();
                    sprite = digitSprites[digit];
                    sL.getRenderer().drawSpriteHUD(sprite, pos, digitData[digit * DIGITMP], digitData[digit * DIGITMP + 1], digitData[digit * DIGITMP + 2]);
                    pos += digitData[digit * DIGITMP + 1] + 1;
                }
            }
        }
    }

}