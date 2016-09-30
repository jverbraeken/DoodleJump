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
import java.util.stream.Collectors;

/**
 * This class describes the scene in which the actual standard game is played.
 */
public class World implements IScene {

    /**
     * The offset of the pause button.
     */
    private static final int PAUSE_OFFSET = 38;
    /**
     * The digit offset multiplier needed for the ScoreText.
     */
    private static final int DIGIT_MULTIPLIER = 3;
    /**
     * The current number system. Standard decimal system.
     */
    private static final int NUMBER_SYSTEM = 10;
    /**
     * The amount of blocks kept in a buffer.
     */
    private static final int BLOCK_BUFFER = 4;
    /**
     * Initial vertical speed for the Doodle.
     */
    private static final int DOODLE_INITIAL_SPEED = -9;
    /**
     * Maximum amount of drawables.
     */
    private static final int MAX_DRAWABLES = 3;

    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * The logger for the World class.
     */
    private final ILogger logger;
    /**
     * Set of all object (excluding Doodle) in the world.
     */
    private final Set<IBlock> blocks = new HashSet<>();
    /**
     * The background of the world.
     */
    private ISprite background;
    /**
     * The Doodle for the world.
     */
    private final IDoodle doodle;
    /**
     * <p>Drawables consists of 3 sets, although this can be easily changed. The first set contains the
     * {@link IRenderable renderables} that will be drawn first (eg platforms), the second set contains the
     * {@link IRenderable renderables} that will be drawn secondly (eg doodles) and the third set contains the
     * {@link IRenderable renderables} that will be drawn at last (eg HUD elements).</p>
     * <p>
     * <p>The reason a list is used instead of an array is because we need to use a weak set. The only
     * way to make it (in Java) is by using Collections.newSetFromMap that creates the set for us (and
     * prohibits creating an array by doing that).</p>
     */
    private final List<Set<IRenderable>> drawables = new ArrayList<>();
    /**
     * List of game objects that should be updated every frame.
     */
    private final Set<IUpdatable> updatables = Collections.newSetFromMap(new WeakHashMap<>());
    /**
     * The highest (and thus latest) created block.
     */
    private IBlock topBlock;

    /**
     * Package visible constructor so a World can only be created via the SceneFactory.
     *
     * @param sL The service locator.
     */
    /* package */ World(final IServiceLocator sL) {
        assert sL != null;
        serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(World.class);

        for (int i = 0; i < MAX_DRAWABLES; i++) {
            drawables.add(Collections.newSetFromMap(new WeakHashMap<>()));
        }

        IBlockFactory blockFactory = sL.getBlockFactory();
        this.topBlock = blockFactory.createStartBlock();
        this.blocks.add(this.topBlock);
        this.drawables.get(0).add(this.topBlock);
        this.updatables.add(this.topBlock);

        for (int i = 1; i < BLOCK_BUFFER; i++) {
            this.topBlock = blockFactory.createBlock(this.topBlock.getTopJumpable());
            this.blocks.add(this.topBlock);
            this.drawables.get(0).add(this.topBlock);
            this.updatables.add(this.topBlock);
        }

        this.background = sL.getSpriteFactory().getBackground();

        this.drawables.get(2).add(new ScoreBar());

        IDoodleFactory doodleFactory = sL.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
        this.doodle.setVerticalSpeed(DOODLE_INITIAL_SPEED);
        this.drawables.get(1).add(this.doodle);
        this.updatables.add(this.doodle);

        serviceLocator.getAudioManager().playStart();

        logger.log("Level started");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        this.serviceLocator.getRenderer().getCamera().setYPos(serviceLocator.getConstants().getGameHeight() / 2d);
        logger.log("The world is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        logger.log("The world is no longer displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        serviceLocator.getRenderer().drawSpriteHUD(this.background, 0, 0);

        for (Set<IRenderable> set : drawables) {
            set.forEach(IRenderable::render);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        updateObjects(delta);
        checkCollisions();
        cleanUp();
        newBlocks();
    }

    /**
     * Update the vertical speed.
     *
     * @param delta The time since the previous update.
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
        if (this.doodle.getVerticalSpeed() > 0) {
            for (IBlock block : blocks) {
                Set<IGameObject> elements = block.getElements();
                for (IGameObject element : elements) {
                    if (this.doodle.checkCollision(element)) {
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
        HashSet<IBlock> toRemove = blocks.stream().filter(e -> e.getTopJumpable().getYPos() > serviceLocator.getRenderer().getCamera().getYPos() + serviceLocator.getConstants().getGameHeight()).collect(Collectors.toCollection(HashSet::new));

        toRemove.forEach(blocks::remove);
    }

    /**
     * Generate new blocks if there are under 3 present.
     */
    private void newBlocks() {
        if (blocks.size() < BLOCK_BUFFER) {
            IJumpable topPlatform = topBlock.getTopJumpable();
            topBlock = serviceLocator.getBlockFactory().createBlock(topPlatform);
            blocks.add(topBlock);
            drawables.get(0).add(topBlock);
            updatables.add(topBlock);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetBackground() {
        background = serviceLocator.getSpriteFactory().getBackground();
    }

    /**
     * IMMUTABLE.
     * <p>
     * The bar on top of the screen displaying the score and pause button
     */
    private final class ScoreBar implements IRenderable {

        /**
         * The transparent and black border at the bottom of the scoreBar that is not take into account when
         * drawing ScoreBar content.
         */
        private static final int SCORE_BAR_DEAD_ZONE = 28;

        /**
         * The scaling of the scoreBar.
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
        private final ILogger logger = serviceLocator.getLoggerFactory().createLogger(ScoreBar.class);

        /**
         * Create a new scoreBar.
         */
        private ScoreBar() {
            scoreBarSprite = serviceLocator.getSpriteFactory().getScoreBarSprite();
            scaling = (double) serviceLocator.getConstants().getGameWidth() / (double) scoreBarSprite.getWidth();
            scoreBarHeight = (int) (scaling * scoreBarSprite.getHeight());

            ISprite[] digitSprites = new ISprite[NUMBER_SYSTEM];
            for (int i = 0; i < NUMBER_SYSTEM; i++) {
                digitSprites[i] = serviceLocator.getSpriteFactory().getDigitSprite(i);
            }
            int scoreX = (int) (digitSprites[2].getWidth() * scaling);
            int scoreY = (int) (scaling * (scoreBarSprite.getHeight() - SCORE_BAR_DEAD_ZONE) / 2);
            scoreText = new ScoreText(scoreX, scoreY, scaling, digitSprites);

            ISprite pauseSprite = serviceLocator.getSpriteFactory().getPauseButtonSprite();
            int pauseX = (int) (serviceLocator.getConstants().getGameWidth() - pauseSprite.getWidth() * scaling - PAUSE_OFFSET);
            int pauseY = (int) (((double) serviceLocator.getConstants().getGameWidth() / (double) scoreBarSprite.getWidth()) * (scoreBarSprite.getHeight() - SCORE_BAR_DEAD_ZONE) / 2 - pauseSprite.getHeight() / 2);
            pauseButton = new PauseButton(pauseX, pauseY, scaling, pauseSprite);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void render() {
            serviceLocator.getRenderer().drawSpriteHUD(scoreBarSprite, 0, 0, serviceLocator.getConstants().getGameWidth(), scoreBarHeight);
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
             *
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
                serviceLocator.getInputManager().addObserver(this);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void render() {
                serviceLocator.getRenderer().drawSpriteHUD(sprite, x, y, width, height);
            }

            /**
             * {@inheritDoc}
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
         * This private subclass creates the text to display the current score.
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
             * separate (integer) variables.
             * <br>
             * [top-Y, width, height] for each of the 10 entries -> total length = 30
             */
            private final byte[] digitData;

            /**
             * Create the score texts.
             *
             * @param xPos    x position.
             * @param yCenter center y of the score text.
             * @param s       the scale.
             * @param dS      the digit sprites.
             */
            private ScoreText(final int xPos, final int yCenter, final double s, final ISprite[] dS) {
                assert dS.length == NUMBER_SYSTEM;
                this.x = xPos;
                digitData = new byte[DIGIT_MULTIPLIER * NUMBER_SYSTEM];
                for (int i = 0; i < NUMBER_SYSTEM; i++) {
                    digitData[i * DIGIT_MULTIPLIER] = (byte) (yCenter - dS[i].getHeight() / 2);
                    digitData[i * DIGIT_MULTIPLIER + 1] = (byte) (dS[i].getWidth() * s);
                    digitData[i * DIGIT_MULTIPLIER + 2] = (byte) (dS[i].getHeight() * s);
                }
                this.digitSprites = dS;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void render() {
                assert doodle.getScore() >= 0;
                int roundedScore = (int) doodle.getScore();
                int digit;
                Stack<Integer> scoreDigits = new Stack<>();
                while (roundedScore != 0) {
                    digit = roundedScore % NUMBER_SYSTEM;
                    roundedScore = roundedScore / NUMBER_SYSTEM;
                    scoreDigits.push(digit);
                }

                int pos = x;
                ISprite sprite;
                while (!scoreDigits.isEmpty()) {
                    digit = scoreDigits.pop();
                    sprite = digitSprites[digit];
                    serviceLocator.getRenderer().drawSpriteHUD(sprite, pos, digitData[digit * DIGIT_MULTIPLIER], digitData[digit * DIGIT_MULTIPLIER + 1], digitData[digit * DIGIT_MULTIPLIER + 2]);
                    pos += digitData[digit * DIGIT_MULTIPLIER + 1] + 1;
                }
            }

        }

    }

}
