package scenes;

import logging.ILogger;
import objects.IGameObject;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import buttons.IButton;
import objects.doodles.Doodle;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import rendering.IDrawable;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * This class describes the scene in which the actual standard game is played.
 */
public class World implements IScene {

    /**
     * The logger for the World class.
     */
    private final ILogger LOGGER;

    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The fastest the doodle can go vertically.
     */
    public static final double V_SPEED_LIMIT = 20;
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
     * The amount of blocks kept in a buffer.
     */
    private final int blockbuffer = 4;
    /**
     * The vertical starting speed.
     */
    private final double startspeed = -9;
    /**
     * Set of all object (excluding Doodle) in the world.
     */
    private final Set<IGameObject> elements = new HashSet<>();
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
     * The vertical speed, negative if going up and positive if going down.
     */
    private double vSpeed = -V_SPEED_LIMIT;
    /**
     * The score for the world.
     */
    private double score = 0;
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
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    /* package */ World(final IServiceLocator sL) {
        assert sL != null;
        this.serviceLocator = sL;
        LOGGER = sL.getLoggerFactory().createLogger(World.class);
        Game.setAlive(true);

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        IBlock lastCreatedBlock = blockFactory.createStartBlock();
        elements.add(lastCreatedBlock);

        for (int i = 1; i < blockbuffer; i++) {
            lastCreatedBlock = blockFactory.createBlock(getTopObject());
            elements.add(lastCreatedBlock);
        }

        background = serviceLocator.getSpriteFactory().getBackground();

        scorebar = new Scorebar();

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
        this.vSpeed = startspeed;

        LOGGER.log("Level started");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
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
    public final void paint() {
        serviceLocator.getRenderer().drawSprite(this.background, 0, 0);

        for (IGameObject e : elements) {
            e.render();
        }

        this.doodle.render();
        this.scorebar.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        updateSpeed();
        updateObjects();
        applySpeed();
        cleanUp();
        newBlocks();
        Game.setAlive(doodle.getYPos() + doodle.getHeight() < Game.HEIGHT);
    }

    /**
     * Update the vertical speed.
     */
    private void updateSpeed() {
        for (IGameObject e : elements) {
            IBlock block = (IBlock) e;
            ArrayList<IGameObject> inside = block.getContent();
            for (IGameObject item : inside) {
                if (vSpeed > 0 && this.doodle.collide(item)) {
                    vSpeed = item.getBoost();
                }
            }
        }

        this.applyGravity();

        this.doodle.setVerticalSpeed(vSpeed);
    }

    /**
     * Apply the current speed to all objects.
     */
    private void applySpeed() {
        if (this.vSpeed < 0 && doodle.getYPos() < Game.HEIGHT / 2 - doodle.getHeight()) {
            for (IGameObject e : elements) {
                e.addYPos(-this.vSpeed);
                score -= this.vSpeed * scoremultiplier;
            }
        } else {
            doodle.addYPos(this.vSpeed);
        }
    }

    /**
     * Applies gravity vAcceleration to the doodle.
     */
    private void applyGravity() {
        this.vSpeed += GRAVITY_ACCELERATION;
    }

    /**
     * Update all objects in world.
     */
    private void updateObjects() {
        for (IGameObject e : elements) {
            e.update();
        }

        doodle.update();
    }

    /**
     * Checks for all the Blocks if they are under over the height of the screen.
     * If that's the case, delete that Block.
     */
    private void cleanUp() {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : elements) {
            if (e.getClass().equals(Doodle.class)) {
                if (e.getYPos() > Game.HEIGHT) {
                    toRemove.add(e);
                }
            } else if (e instanceof IBlock) {
                ((IBlock) e).cleanUpPlatforms();
                if (e.getYPos() > Game.HEIGHT) {
                    toRemove.add(e);
                }
            }
        }

        for (IGameObject e : toRemove) {
            elements.remove(e);
        }
    }

    /**
     * Generate new blocks if there are under 3 present.
     */
    private void newBlocks() {
        if (elements.size() < blockbuffer) {
            double minY = Double.MAX_VALUE;
            for (IGameObject e : elements) {
                if (e.getYPos() < minY) {
                    minY = e.getYPos();
                }
            }
            IGameObject topObject = getTopObject();
            //TODO: implements New Block
            elements.add(serviceLocator.getBlockFactory().createBlock(topObject));
        }
    }

    /**
     * Return the object that is currently highest in world.
     * @return the highest object.
     */
    private IGameObject getTopObject() {
        IBlock topBlock = (IBlock) elements.iterator().next();
        for (IGameObject e : elements) {
            if (e.getYPos() < topBlock.getYPos()) {
                topBlock = (IBlock) e;
            }
        }
        ArrayList<IGameObject> arr = topBlock.getContent();
        IGameObject topObject = arr.get(arr.size() - 1);

        return topObject;
    }

    /**
     * IMMUTABLE.
     * <p>
     * The bar on top of the screen displaying the score and pause button
     */
    private final class Scorebar {
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
        private final ILogger logger = serviceLocator.getLoggerFactory().createLogger(Scorebar.class);

        /**
         * Create a new scorebar.
         */
        private Scorebar() {
            scoreBarSprite = serviceLocator.getSpriteFactory().getScorebarSprite();
            scaling = (double) Game.WIDTH / (double) scoreBarSprite.getWidth();
            scoreBarHeight = (int) (scaling * scoreBarSprite.getHeight());

            ISprite[] digitSprites = new ISprite[NUMBERSYSTEM];
            for (int i = 0; i < NUMBERSYSTEM; i++) {
                digitSprites[i] = serviceLocator.getSpriteFactory().getDigitSprite(i);
            }
            int scoreX = (int) (digitSprites[2].getWidth() * scaling);
            int scoreY = (int) (scaling * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2);
            scoreText = new ScoreText(scoreX, scoreY, scaling, digitSprites);

            ISprite pauseSprite = serviceLocator.getSpriteFactory().getPauseButtonSprite();
            int pauseX = (int) (Game.WIDTH - pauseSprite.getWidth() * scaling - pauseoffset);
            int pauseY = (int) (((double) Game.WIDTH / (double) scoreBarSprite.getWidth()) * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2 - pauseSprite.getHeight() / 2);
            pauseButton = new PauseButton(pauseX, pauseY, scaling, pauseSprite);

            serviceLocator.getInputManager().addObserver(pauseButton);
        }

        /**
         * Render this object.
         */
        private void render() {
            serviceLocator.getRenderer().drawSprite(scoreBarSprite, 0, 0, Game.WIDTH, scoreBarHeight);
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
            }

            /**
             * Render the object.
             */
            @Override
            public void render() {
                serviceLocator.getRenderer().drawSprite(sprite, x, y, width, height);
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
        private final class ScoreText implements IDrawable {
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
                int roundedScore = (int) score;
                int digit;
                Stack<Integer> scoreDigits = new Stack<>();

                if (roundedScore == 0) {
                    scoreDigits.push(0);
                }
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
                    serviceLocator.getRenderer().drawSprite(sprite, pos, digitData[digit * DIGITMP], digitData[digit * DIGITMP + 1], digitData[digit * DIGITMP + 2]);
                    pos += digitData[digit * DIGITMP + 1] + 1;
                }
            }
        }
    }

}
