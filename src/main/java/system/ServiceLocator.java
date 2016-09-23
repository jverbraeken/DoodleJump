package system;

import constants.IConstants;
import filesystem.IFileSystem;
import input.IInputManager;
import logging.ILogger;
import logging.ILoggerFactory;
import math.ICalc;
import objects.blocks.IBlockFactory;
import objects.blocks.platform.IPlatformFactory;
import objects.buttons.IButtonFactory;
import objects.doodles.IDoodleFactory;
import objects.enemies.IEnemyBuilder;
import objects.powerups.IPowerupFactory;
import rendering.IRenderer;
import resources.IRes;
import resources.audio.IAudioManager;
import resources.sprites.ISpriteFactory;
import scenes.ISceneFactory;

/* package */ class ServiceLocator implements IServiceLocator {

    // constants
    private IConstants constants;

    // input
    private IInputManager inputManager;

    // resources.audio
    private IAudioManager audioManager;

    // rendering
    private IRenderer renderer;
    private IButtonFactory buttonFactory;

    // filesystem
    private IFileSystem fileSystem;

    // resources\
    private ISpriteFactory spriteFactory;
    private IRes res;

    // objects.enemies
    private IEnemyBuilder enemyBuilder;

    // util
    private ICalc calc;
    private ILoggerFactory loggerFactory;

    // objects
    private IPowerupFactory powerupFactory;
    private IDoodleFactory doodleFactory;
    private IBlockFactory blockFactory;
    private IPlatformFactory platformFactory;

    // scenes
    private ISceneFactory sceneFactory;

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
    public void provide(ILoggerFactory loggerFactory) {
        assert loggerFactory != null;
        this.loggerFactory = loggerFactory;
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
    public void provide(IConstants constants) {
        assert buttonFactory != null;
        this.constants = constants;
    }









    /**
     * {@inheritDoc}
     */
    @Override
    public IAudioManager getAudioManager() {
        assert audioManager != null;
        return audioManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEnemyBuilder getEnemyBuilder() {
        assert enemyBuilder != null;
        return enemyBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IFileSystem getFileSystem() {
        assert fileSystem != null;
        return fileSystem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IInputManager getInputManager() {
        assert inputManager != null;
        return inputManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICalc getCalc() {
        assert calc != null;
        return calc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBlockFactory getBlockFactory() {
        assert blockFactory != null;
        return blockFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodleFactory getDoodleFactory() {
        assert doodleFactory != null;
        return doodleFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPowerupFactory getPowerupFactory() {
        assert powerupFactory != null;
        return powerupFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRenderer getRenderer() {
        assert renderer != null;
        return renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISpriteFactory getSpriteFactory() {
        assert spriteFactory != null;
        return spriteFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILoggerFactory getLoggerFactory() {
        assert loggerFactory != null;
        return loggerFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISceneFactory getSceneFactory() {
        assert sceneFactory != null;
        return sceneFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatformFactory getPlatformFactory() {
        assert platformFactory != null;
        return platformFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRes getRes() {
        assert res != null;
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButtonFactory getButtonFactory() {
        assert buttonFactory != null;
        return buttonFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IConstants getConstants() {
        assert constants != null;
        return constants;
    }

}