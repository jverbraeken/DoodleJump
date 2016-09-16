package system;

import objects.ICollisions;
import objects.buttons.IButtonFactory;
import resources.IRes;
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
import scenes.ISceneFactory;
import resources.sprites.ISpriteFactory;

/* package */ class ServiceLocator implements IServiceLocator {

    // input
    private IInputManager inputManager;
    private IInputManager keyInputManager;

    // resources.audio
    private IAudioManager audioManager;

    // rendering
    private IRenderer renderer;
    private IButtonFactory buttonFactory;

    // filesystem
    private IFileSystem fileSystem;

    // resources
    private ILevelBuilder levelFactory;
    private ISpriteFactory spriteFactory;
    private IRes res;

    // objects.enemies
    private IEnemyBuilder enemyBuilder;

    // util
    private ICalc calc;

    // objects
    private IPowerupFactory powerupFactory;
    private IDoodleFactory doodleFactory;
    private IBlockFactory blockFactory;
    private ILevelBuilder levelBuilder;
    private IPlatformFactory platformFactory;
    private ICollisions collisions;

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
    public void provide(ILevelBuilder levelBuilder) {
        assert levelBuilder != null;
        this.levelBuilder = levelBuilder;
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
    public ILevelBuilder getLevelBuilder() {
        return levelBuilder;
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

}
