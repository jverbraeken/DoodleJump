package system;

import buttons.ButtonFactory;
import filesystem.FileSystem;
import input.InputManager;
import logging.ILoggerFactory;
import logging.LoggerFactory;
import math.Calc;
import objects.Collisions;
import objects.ICollisions;
import buttons.IButtonFactory;
import objects.blocks.BlockFactory;
import objects.blocks.platform.PlatformFactory;
import objects.doodles.DoodleFactory;
import objects.enemies.EnemyBuilder;
import objects.powerups.PowerupFactory;
import rendering.Renderer;
import resources.IRes;
import resources.Res;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import objects.enemies.IEnemyBuilder;
import filesystem.IFileSystem;
import input.IInputManager;
import math.ICalc;
import objects.blocks.IBlockFactory;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodleFactory;
import objects.powerups.IPowerupFactory;
import rendering.IRenderer;
import resources.sprites.SpriteFactory;
import scenes.ISceneFactory;
import resources.sprites.ISpriteFactory;
import scenes.SceneFactory;

/**
 * Default implementation for the ServiceLocator. Used to gain access to all services.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
/* package */ class ServiceLocator implements IServiceLocator {

    // audio
    private IAudioManager audioManager;

    // filesystem
    private IFileSystem fileSystem;

    // input
    private IInputManager inputManager;

    // logger
    private ILoggerFactory loggerFactory;

    // OBJECTS //
    // blocks
    private IBlockFactory blockFactory;
    private IPlatformFactory platformFactory;
    // collisions
    private ICollisions collisions;
    // doodle
    private IDoodleFactory doodleFactory;
    // enemies
    private IEnemyBuilder enemyBuilder;
    // powerup
    private IPowerupFactory powerupFactory;

    // rendering
    private IRenderer renderer;
    private IButtonFactory buttonFactory;

    // resources
    private ISpriteFactory spriteFactory;
    private IRes res;

    // scenes
    private ISceneFactory sceneFactory;

    // utility
    private ICalc calc;

    /**
     * Initialize the ServiceLocator class.
     */
    /* package */ ServiceLocator() {
        this.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IAudioManager aM) {
        assert aM != null;
        this.audioManager = aM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IEnemyBuilder eB) {
        assert eB != null;
        this.enemyBuilder = eB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IFileSystem fS) {
        assert fS != null;
        this.fileSystem = fS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IInputManager iM) {
        assert iM != null;
        this.inputManager = iM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final ICalc c) {
        assert c != null;
        this.calc = c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IBlockFactory bF) {
        assert bF != null;
        this.blockFactory = bF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IDoodleFactory dF) {
        assert dF != null;
        this.doodleFactory = dF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IPowerupFactory pF) {
        assert pF != null;
        this.powerupFactory = pF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IRenderer r) {
        assert r != null;
        this.renderer = r;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final ISpriteFactory sF) {
        assert sF != null;
        this.spriteFactory = sF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final ISceneFactory sF) {
        assert sF != null;
        this.sceneFactory = sF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IPlatformFactory pF) {
        assert pF != null;
        this.platformFactory = pF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IRes r) {
        assert r != null;
        this.res = r;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IButtonFactory bF) {
        assert bF != null;
        this.buttonFactory = bF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final ICollisions c) {
        assert c != null;
        this.collisions = c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(ILoggerFactory loggerFactory) {
        assert loggerFactory != null;
        this.loggerFactory = loggerFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAudioManager getAudioManager() {
        return audioManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEnemyBuilder getEnemyBuilder() {
        return enemyBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IFileSystem getFileSystem() {
        return fileSystem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IInputManager getInputManager() {
        return inputManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICalc getCalc() {
        return calc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBlockFactory getBlockFactory() {
        return blockFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodleFactory getDoodleFactory() {
        return doodleFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPowerupFactory getPowerupFactory() {
        return powerupFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRenderer getRenderer() {
        return renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISpriteFactory getSpriteFactory() {
        return spriteFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISceneFactory getSceneFactory() {
        return sceneFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatformFactory getPlatformFactory() {
        return platformFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRes getRes() {
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButtonFactory getButtonFactory() {
        return buttonFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICollisions getCollisions() {
        return collisions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILoggerFactory getLoggerFactory() { return loggerFactory; }

    /**
     * Initialize the ServiceLocator.
     */
    private void init() {
        FileSystem.register(this);
        LoggerFactory.register(this);
        AudioManager.register(this);
        EnemyBuilder.register(this);
        InputManager.register(this);
        Calc.register(this);
        BlockFactory.register(this);
        DoodleFactory.register(this);
        PowerupFactory.register(this);
        SpriteFactory.register(this);
        Renderer.register(this);
        SceneFactory.register(this);
        PlatformFactory.register(this);
        Res.register(this);
        ButtonFactory.register(this);
        Collisions.register(this);
    }

}
