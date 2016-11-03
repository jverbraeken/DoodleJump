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
import progression.IProgressionManager;
import progression.ProgressionManager;
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
    private final Map<DrawableLevels, Set<IRenderable>> newDrawables = new EnumMap<>(DrawableLevels.class);
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

        this.serviceLocator = sL;
        this.logger = sL.getLoggerFactory().createLogger(World.class);

        this.drawables.put(DrawableLevels.back, Collections.newSetFromMap(new WeakHashMap<>()));
        this.drawables.put(DrawableLevels.middle, Collections.newSetFromMap(new WeakHashMap<>()));
        this.drawables.put(DrawableLevels.front, Collections.newSetFromMap(new WeakHashMap<>()));
        this.newDrawables.put(DrawableLevels.back, Collections.newSetFromMap(new WeakHashMap<>()));
        this.newDrawables.put(DrawableLevels.middle, Collections.newSetFromMap(new WeakHashMap<>()));
        this.newDrawables.put(DrawableLevels.front, Collections.newSetFromMap(new WeakHashMap<>()));

        IBlockFactory blockFactory = sL.getBlockFactory();
        this.topBlock = blockFactory.createStartBlock();
        this.blocks.add(this.topBlock);
        this.newDrawables.get(DrawableLevels.back).add(this.topBlock);
        this.newUpdatables.add(this.topBlock);

        for (int i = 1; i < 2; i++) {
            this.topBlock = blockFactory.createBlock(this.topBlock.getTopJumpable(), BlockTypes.normalOnlyBlock, false);
            this.blocks.add(this.topBlock);
            this.newDrawables.get(DrawableLevels.back).add(this.topBlock);
            this.newUpdatables.add(this.topBlock);
        }

        this.background = sL.getSpriteFactory().getBackground();
        this.scoreBar = new ScoreBar();
        this.newDrawables.get(DrawableLevels.front).add(this.scoreBar);

        this.serviceLocator.getAudioManager().playStart();
        this.serviceLocator.getAudioManager().loopThemeSong();

        this.start();
        this.logger.info("Level started");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start() {
        this.register();
        this.logger.info("The world is now displaying");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void stop() {
        this.deregister();
        this.logger.info("The world scene is stopped");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void register() {
        this.serviceLocator.getRenderer().getCamera().setYPos(serviceLocator.getConstants().getGameHeight() / 2d);
        this.scoreBar.register();
        this.doodles.forEach(IDoodle::register);
        this.logger.info("The world is now registered");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void deregister() {
        this.scoreBar.deregister();
        this.doodles.forEach(IDoodle::deregister);
        this.logger.info("The world scene is stopped");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        this.serviceLocator.getRenderer().drawSpriteHUD(this.background, new Point(0, 0));

        this.drawables.get(DrawableLevels.back).addAll(this.newDrawables.get(DrawableLevels.back));
        this.drawables.get(DrawableLevels.middle).addAll(this.newDrawables.get(DrawableLevels.middle));
        this.drawables.get(DrawableLevels.front).addAll(this.newDrawables.get(DrawableLevels.front));
        this.newDrawables.get(DrawableLevels.back).clear();
        this.newDrawables.get(DrawableLevels.middle).clear();
        this.newDrawables.get(DrawableLevels.front).clear();

        this.drawables.get(DrawableLevels.back).forEach(IRenderable::render);
        this.drawables.get(DrawableLevels.middle).forEach(IRenderable::render);
        this.drawables.get(DrawableLevels.front).forEach(IRenderable::render);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
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
        this.newDrawables.get(DrawableLevels.middle).add(renderable);
    }

    /**
     * Add a new updatable element to the World.
     *
     * @param updatable An object implementing the IUpdatable interface.
     */
    public final void addUpdatable(final IUpdatable updatable) {
        this.newUpdatables.add(updatable);
    }

    /**
     * End the game.
     *
     * @param score The score the player got.
     */
    public final void endGameInstance(final double score, final double extraExp) {
        IProgressionManager progressionManager = this.serviceLocator.getProgressionManager();
        progressionManager.addHighScore("Doodle", score);
        progressionManager.addExperience((int) score);
        this.serviceLocator.getAudioManager().stopLoopingThemeSong();

        Game.setScene(this.serviceLocator.getSceneFactory().createKillScreen((int) score, (int) extraExp));
    }

    /**
     * Add a Doodle to the world.
     *
     * @param doodle The Doodle to add.
     */
    final void addDoodle(final IDoodle doodle) {
        this.doodles.add(doodle);
        this.newUpdatables.add(doodle);
        this.newDrawables.get(DrawableLevels.middle).add(doodle);
    }

    /**
     * Update the vertical speed.
     *
     * @param delta The time since the previous update.
     */
    private void updateObjects(final double delta) {
        this.updatables.addAll(this.newUpdatables);
        this.newUpdatables.clear();

        for (IUpdatable updatable : this.updatables) {
            updatable.update(delta);
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
            for (IBlock block : this.blocks) {
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
        if (this.blocks.size() < World.BLOCK_BUFFER) {
            IJumpable topPlatform = this.topBlock.getTopJumpable();
            this.topBlock = this.serviceLocator.getBlockFactory()
                    .createBlock(topPlatform, BlockTypes.randomType(), this.doodles.size() < 2);
            this.blocks.add(this.topBlock);
            this.newDrawables.get(DrawableLevels.back).add(this.topBlock);
            this.newUpdatables.add(this.topBlock);
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
        /* package */ ScoreBar() {
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
     * Activate the input observers of doodles that are active in this scene.
     */
    public void registerDoodles() {
        this.doodles.forEach(IDoodle::register);
    }

    /**
     * Deactivate the input observers of doodles that are active in this scene.
     */
    public void deregisterDoodles() {
        this.doodles.forEach(IDoodle::deregister);
    }

}
