package system;

import filesystem.IFileSystem;
import input.IInputManager;
import math.ICalc;
import objects.ICollisions;
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

@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
/* package */ class ServiceLocator implements IServiceLocator {

    // input
    private IInputManager inputManager;

    // resources.audio
    private IAudioManager audioManager;

    // rendering
    private IRenderer renderer;
    private IButtonFactory buttonFactory;

    // filesystem
    private IFileSystem fileSystem;

    // resources
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
    private IPlatformFactory platformFactory;
    private ICollisions collisions;

    // scenes
    private ISceneFactory sceneFactory;

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

}
