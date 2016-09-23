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
import objects.doodles.IDoodleFactory;
import objects.blocks.platform.IPlatformFactory;
import objects.powerups.IPowerupFactory;
import rendering.IRenderer;
import resources.sprites.SpriteFactory;
import scenes.ISceneFactory;
import resources.sprites.ISpriteFactory;
import scenes.SceneFactory;

/**
 * Default implementation for the ServiceLocator. Used to gain access to all services.
 */
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
    public void provide(IAudioManager audioManager) {
        assert audioManager != null;
        this.audioManager = audioManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IEnemyBuilder enemyBuilder) {
        assert enemyBuilder != null;
        this.enemyBuilder = enemyBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IFileSystem fileSystem) {
        assert fileSystem != null;
        this.fileSystem = fileSystem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IInputManager inputManager) {
        assert inputManager != null;
        this.inputManager = inputManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(ICalc calc) {
        assert calc != null;
        this.calc = calc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IBlockFactory blockFactory) {
        assert blockFactory != null;
        this.blockFactory = blockFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IDoodleFactory doodleFactory) {
        assert doodleFactory != null;
        this.doodleFactory = doodleFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IPowerupFactory powerupFactory) {
        assert powerupFactory != null;
        this.powerupFactory = powerupFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IRenderer renderer) {
        assert renderer != null;
        this.renderer = renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(ISpriteFactory spriteFactory) {
        assert spriteFactory != null;
        this.spriteFactory = spriteFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(ISceneFactory sceneFactory) {
        assert sceneFactory != null;
        this.sceneFactory = sceneFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IPlatformFactory platformFactory) {
        assert platformFactory != null;
        this.platformFactory = platformFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IRes res) {
        assert res != null;
        this.res = res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(IButtonFactory buttonFactory) {
        assert buttonFactory != null;
        this.buttonFactory = buttonFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(ICollisions collisions) {
        assert collisions != null;
        this.collisions = collisions;
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
    public IButtonFactory getButtonFactory() { return buttonFactory; }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICollisions getCollisions() { return collisions; }

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
