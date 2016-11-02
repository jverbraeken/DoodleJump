package scenes;

import buttons.IButton;
import logging.ILogger;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.BlockTypes;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.doodles.IDoodle;
import objects.enemies.AEnemy;
import objects.powerups.Powerups;
import rendering.AccelerationType;
import rendering.ICamera;
import resources.sprites.ISprite;
import system.Game;
import system.IRenderable;
import system.IServiceLocator;
import system.IUpdatable;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * This class describes the scene in which the actual standard game is played.
 */
public final class World implements IScene {

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
     * The amount of experience earned from killing an enemy.
     */
    private static final int EXP_KILLING_ENEMY = 200;

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
     * The Doodle for the world.
     */
    private final List<IDoodle> doodles = new ArrayList<>();
    /**
     * <p>Drawables consists of 3 sets, although this can be easily changed. The first set contains the
     * {@link IRenderable renderables} that will be drawn first (eg platforms), the second set contains the
     * {@link IRenderable renderables} that will be drawn secondly (eg doodles) and the third set contains the
     * {@link IRenderable renderables} that will be drawn at last (eg HUD elements).</p>
     * <p>The reason a list is used instead of an array is because we need to use a weak set. The only
     * way to make it (in Java) is by using Collections.newSetFromMap that creates the set for us (and
     * prohibits creating an array by doing that).</p>
     */
    private final Map<DrawableLevels, Set<IRenderable>> drawables = new EnumMap<>(DrawableLevels.class);
    /**
     * A set of drawables that should be added next time the World is rendered.
     */
    private final Set<IRenderable> newDrawables = new HashSet<>();
    /**
     * List of game objects that should be updated every frame.
     */
    private final Set<IUpdatable> updatables = Collections.newSetFromMap(new WeakHashMap<>());
    /**
     * A set of drawables that should be added next time the World is rendered.
     */
    private final ArrayList<IUpdatable> newUpdatables = new ArrayList<>();
    /**
     * The background of the world.
     */
    private final ISprite background;
    /**
     * The top bar displaying the score and a pause button.
     */
    private final ScoreBar scoreBar;
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
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }

        serviceLocator = sL;
        logger = sL.getLoggerFactory().createLogger(World.class);

        this.drawables.put(DrawableLevels.back, Collections.newSetFromMap(new WeakHashMap<>()));
        this.drawables.put(DrawableLevels.middle, Collections.newSetFromMap(new WeakHashMap<>()));
        this.drawables.put(DrawableLevels.front, Collections.newSetFromMap(new WeakHashMap<>()));

        IBlockFactory blockFactory = sL.getBlockFactory();
        this.topBlock = blockFactory.createStartBlock();
        this.blocks.add(this.topBlock);
        this.drawables.get(DrawableLevels.back).add(this.topBlock);
        this.updatables.add(this.topBlock);

        for (int i = 1; i < BLOCK_BUFFER; i++) {
            this.topBlock = blockFactory.createBlock(this.topBlock.getTopJumpable(), BlockTypes.normalOnlyBlock);
            this.blocks.add(this.topBlock);
            this.drawables.get(DrawableLevels.back).add(this.topBlock);
            this.updatables.add(this.topBlock);
        }

        this.background = sL.getSpriteFactory().getBackground();
        this.scoreBar = new ScoreBar();
        this.drawables.get(DrawableLevels.front).add(this.scoreBar);

        serviceLocator.getAudioManager().playStart();

        this.start();
        logger.info("Level started");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        this.serviceLocator.getRenderer().getCamera().setYPos(serviceLocator.getConstants().getGameHeight() / 2d);
        this.scoreBar.register();
        for (IDoodle doodle : this.doodles) {
            doodle.register();
        }

        logger.info("The world is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        this.scoreBar.deregister();
        for (IDoodle doodle : this.doodles) {
            doodle.deregister();
        }
        logger.info("The world scene is stopped");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        serviceLocator.getRenderer().drawSpriteHUD(this.background, new Point(0, 0));

        drawables.get(DrawableLevels.back).addAll(newDrawables);
        newDrawables.clear();

        drawables.get(DrawableLevels.back).forEach(IRenderable::render);
        drawables.get(DrawableLevels.middle).forEach(IRenderable::render);
        drawables.get(DrawableLevels.front).forEach(IRenderable::render);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        this.updatables.addAll(this.newUpdatables);

        this.updateObjects(delta);
        this.cleanUp();
        this.newBlocks();
        this.checkCollisions();
        this.updateCameraSpeed();
    }

    /**
     * Add a new drawable element to the World.
     *
     * @param renderable An object implementing the IRenderable interface.
     */
    public final void addDrawable(final IRenderable renderable) {
        newDrawables.add(renderable);
    }

    /**
     * Add a new updatable element to the World.
     *
     * @param updatable An object implementing the IUpdatable interface.
     */
    public final void addUpdatable(final IUpdatable updatable) {
        newUpdatables.add(updatable);
    }

    /**
     * End the game.
     *
     * @param score The score the player got.
     */
    public final void endGameInstance(final double score, final double extraExp) {
        serviceLocator.getProgressionManager().addHighScore("Doodle", score);
        serviceLocator.getProgressionManager().addExperience((int) score);

        Game.setScene(serviceLocator.getSceneFactory().createKillScreen((int) score, (int) extraExp));
    }

    /**
     * Add a Doodle to the world.
     *
     * @param doodle The Doodle to add.
     */
    final void addDoodle(final IDoodle doodle) {
        this.doodles.add(doodle);
        this.updatables.add(doodle);
        this.drawables.get(DrawableLevels.middle).add(doodle);
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
     * Check the collisions for all the Doodles in the world.
     */
    private void checkCollisions() {
        this.doodles.forEach(this::checkCollisions);
    }

    /**
     * Check the collisions in the World for a specific Doodle.
     *
     * @param doodle The Doodle to check the collisions for.
     */
    // We suppress the PMD warning here because there's a bug in PMD that causes it to not noticing the usage of this
    // private method in {@link #checkCollisions()}
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void checkCollisions(final IDoodle doodle) {
        assert doodle != null;
        if (doodle.isAlive()) {
            for (IBlock block : blocks) {
                Set<IGameObject> elements = block.getElements();
                for (IGameObject element : elements) {
                    if (doodle.checkCollision(element)) {
                        element.collidesWith(doodle);
                    }
                    if (element instanceof AEnemy) {
                        AEnemy enemy = (AEnemy) element;
                        HashSet<IGameObject> projectilesToRemove = new HashSet<>();
                        for (IGameObject projectile : doodle.getProjectiles()) {
                            if (projectile.checkCollision(enemy)) {
                                enemy.setXPos(Integer.MAX_VALUE);
                                projectilesToRemove.add(projectile);
                                doodle.addExperiencePoints(enemy.getAmountOfExperience());
                            }
                        }
                        for (IGameObject projectile : projectilesToRemove) {
                            doodle.removeProjectile(projectile);
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
        final double yThreshold = serviceLocator.getRenderer().getCamera().getYPos() + serviceLocator.getConstants().getGameHeight();
        HashSet<IBlock> toRemove = blocks.stream().filter(e -> e.getTopJumpable().getYPos() > yThreshold).collect(Collectors.toCollection(HashSet::new));

        toRemove.forEach(blocks::remove);
    }

    /**
     * Generate new blocks if there are under 3 present.
     */
    private void newBlocks() {
        if (blocks.size() < BLOCK_BUFFER) {
            IJumpable topPlatform = topBlock.getTopJumpable();
            this.topBlock = serviceLocator.getBlockFactory().createBlock(topPlatform, BlockTypes.randomType());
            this.blocks.add(topBlock);
            this.drawables.get(DrawableLevels.back).add(topBlock);
            this.updatables.add(topBlock);
        }
    }

    /**
     * The levels on which a drawable can be drawn.
     */
    private enum DrawableLevels {
        /**
         * The things that should be drawn at the front.
         */
        front,

        /**
         * The GameObjects that should be drawn behind the front, but in front of everything else (such as doodles).
         */
        middle,

        /**
         * The things that should be drawn in the back (such as platforms).
         */
        back
    }

    /**
     * Update the camera speed based on Doodles locations.
     */
    private void updateCameraSpeed() {
        final ICamera camera = this.serviceLocator.getRenderer().getCamera();
        final double camY = camera.getYPos();
        for (IDoodle doodle : this.doodles) {
            if (camY < doodle.getYPos() + doodle.getSprite().getHeight()) {
                camera.setAccelerationType(AccelerationType.normal);
                return;
            }
        }

        camera.setAccelerationType(AccelerationType.fast);
    }

    /**
     * IMMUTABLE.
     * <br>
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
        private final IButton pauseButton;
        /**
         * The text display of the current score.
         */
        private final ScoreText scoreText;

        /**
         * Create a new scoreBar.
         */
        public ScoreBar() {
            this.scoreBarSprite = World.this.serviceLocator.getSpriteFactory().getScoreBarSprite();
            this.scaling = (double) World.this.serviceLocator.getConstants().getGameWidth()
                    / (double) this.scoreBarSprite.getWidth();
            this.scoreBarHeight = (int) (this.scaling * this.scoreBarSprite.getHeight());

            ISprite[] digitSprites = World.this.serviceLocator.getSpriteFactory().getDigitSprites();
            int scoreX = (int) (digitSprites[2].getWidth() * this.scaling);
            int scoreY = (int) (this.scaling * (this.scoreBarSprite.getHeight() - ScoreBar.SCORE_BAR_DEAD_ZONE) / 2d);
            this.scoreText = new ScoreText(scoreX, scoreY, this.scaling, digitSprites);

            ISprite pauseSprite = World.this.serviceLocator.getSpriteFactory().getPauseButtonSprite();
            final int gameWidth = World.this.serviceLocator.getConstants().getGameWidth();
            double pauseX = 1d - pauseSprite.getWidth() * this.scaling / gameWidth - World.PAUSE_OFFSET * this.scaling / gameWidth;
            double pauseY = this.scaling * (this.scoreBarSprite.getHeight() - ScoreBar.SCORE_BAR_DEAD_ZONE) / 2d / gameWidth
                    - (double) pauseSprite.getHeight() * this.scaling / 2d / gameWidth;
            this.pauseButton = World.this.serviceLocator.getButtonFactory().createPauseButton(pauseX, pauseY);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void render() {
            World.this.serviceLocator.getRenderer().drawSpriteHUD(this.scoreBarSprite, new Point(0, 0),
                    World.this.serviceLocator.getConstants().getGameWidth(), this.scoreBarHeight);
            this.scoreText.render();
            this.pauseButton.render();
        }

        /**
         * Registers its button to the {@link input.IInputManager input manager}.
         */
        private void register() {
            this.pauseButton.register();
        }

        /**
         * Deregisters its button from the {@link input.IInputManager input manager}.
         */
        private void deregister() {
            this.pauseButton.deregister();
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
             * @param scalar  the scale.
             * @param dS      the digit sprites.
             */
            private ScoreText(final int xPos, final int yCenter, final double scalar, final ISprite[] dS) {
                assert dS.length == World.NUMBER_SYSTEM;
                this.x = xPos;
                this.digitData = new byte[World.DIGIT_MULTIPLIER * World.NUMBER_SYSTEM];
                for (int i = 0; i < NUMBER_SYSTEM; i++) {
                    this.digitData[i * World.DIGIT_MULTIPLIER] = (byte) (yCenter - dS[i].getHeight() / 2);
                    this.digitData[i * World.DIGIT_MULTIPLIER + 1] = (byte) (dS[i].getWidth() * scalar);
                    this.digitData[i * World.DIGIT_MULTIPLIER + 2] = (byte) (dS[i].getHeight() * scalar);
                }
                this.digitSprites = dS;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void render() {
                int roundedScore = this.getHighestScore();
                int digit;
                Stack<Integer> scoreDigits = new Stack<>();
                while (roundedScore != 0) {
                    digit = roundedScore % World.NUMBER_SYSTEM;
                    roundedScore = roundedScore / World.NUMBER_SYSTEM;
                    scoreDigits.push(digit);
                }

                int pos = this.x;
                ISprite sprite;
                while (!scoreDigits.isEmpty()) {
                    digit = scoreDigits.pop();
                    sprite = this.digitSprites[digit];
                    World.this.serviceLocator.getRenderer().drawSpriteHUD(sprite,
                            new Point(pos,
                                    this.digitData[digit * World.DIGIT_MULTIPLIER]),
                            this.digitData[digit * World.DIGIT_MULTIPLIER + 1],
                            this.digitData[digit * World.DIGIT_MULTIPLIER + 2]);
                    pos += this.digitData[digit * World.DIGIT_MULTIPLIER + 1] + 1;
                }
            }

            /**
             * Get the highest score of all the Doodles in the Game.
             *
             * @return The highest score in the game.
             */
            private int getHighestScore() {
                int highestScore = 0;
                for (IDoodle doodle : World.this.doodles) {
                    highestScore = Math.max(highestScore, (int) doodle.getScore());
                }

                return highestScore;
            }

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchDisplay(PauseScreenModes mode) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateButton(final Powerups powerup, final double x, final double y) {
    }

    /**
     * Activate the input observers of doodles that are active in this scene.
     */
    public void registerDoodle() {
        for (IDoodle doodle : doodles) {
            doodle.register();
        }
    }

    /**
     * Deactivate the input observers of doodles that are active in this scene.
     */
    public void deregisterDoodle() {
        for (IDoodle doodle : doodles) {
            doodle.deregister();
        }
    }

}
