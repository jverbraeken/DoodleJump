package system;

import buttons.ButtonFactory;
import buttons.IButtonFactory;
import constants.Constants;
import constants.IConstants;
import filesystem.FileSystem;
import filesystem.IFileSystem;
import input.IInputManager;
import input.InputManager;
import logging.ILoggerFactory;
import logging.LoggerFactory;
import math.Calc;
import math.ICalc;
import objects.blocks.BlockFactory;
import objects.blocks.IBlockFactory;
import objects.blocks.platform.IPlatformFactory;
import objects.blocks.platform.PlatformFactory;
import objects.doodles.DoodleFactory;
import objects.doodles.IDoodleFactory;
import objects.enemies.EnemyFactory;
import objects.enemies.IEnemyFactory;
import objects.powerups.IPowerupFactory;
import objects.powerups.PowerupFactory;
import progression.IMissionFactory;
import progression.IProgressionManager;
import progression.MissionFactory;
import progression.ProgressionManager;
import rendering.CameraFactory;
import rendering.ICameraFactory;
import rendering.IRenderer;
import rendering.Renderer;
import resources.IRes;
import resources.Res;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
import scenes.ISceneFactory;
import scenes.SceneFactory;

/**
 * Default implementation for the ServiceLocator. Used to gain access to all services.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
/* package */ final class ServiceLocator implements IServiceLocator {

    /**
     * The singleton serviceLocator.
     * Constructed eagerly.
     */
    private static final ServiceLocator SERVICE_LOCATOR = new ServiceLocator();
    // constants.json
    private IConstants constants;
    // audio
    private IAudioManager audioManager;
    // input
    private IInputManager inputManager;
    // enemies
    private IEnemyFactory enemyFactory;
    // rendering
    private ICameraFactory cameraFactory;
    private IRenderer renderer;
    private IButtonFactory buttonFactory;
    // filesystem
    private IFileSystem fileSystem;
    // util
    private ILoggerFactory loggerFactory;
    // progression
    private IProgressionManager progressionManager;
    private IMissionFactory missionFactory;
    // objects
    private IPowerupFactory powerupFactory;
    private IDoodleFactory doodleFactory;
    private IBlockFactory blockFactory;
    private IPlatformFactory platformFactory;
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
    /* package */
    private ServiceLocator() {
        this.init();
    }

    /**
     * Getter of the singleton service locator.
     *
     * @return the service locator.
     */
    public static IServiceLocator getServiceLocator() {
        return SERVICE_LOCATOR;
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
    public void provide(final IEnemyFactory eF) {
        assert eF != null;
        this.enemyFactory = eF;
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
    public void provide(final ILoggerFactory lF) {
        assert lF != null;
        this.loggerFactory = lF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final ICameraFactory cF) {
        assert cF != null;
        this.cameraFactory = cF;
    }

    /**
     * {@inheritDoc}
     */
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
    public void provide(final IConstants c) {
        assert c != null;
        this.constants = c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IProgressionManager progressionManager) {
        assert progressionManager != null;
        this.progressionManager = progressionManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IMissionFactory missionFactory) {
        assert missionFactory != null;
        this.missionFactory = missionFactory;
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
    public IEnemyFactory getEnemyFactory() {
        assert enemyFactory != null;
        return enemyFactory;
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
    public IProgressionManager getProgressionManager() {
        assert progressionManager != null;
        return progressionManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionFactory getMissionFactory() {
        assert missionFactory != null;
        return missionFactory;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ICameraFactory getCameraFactory() {
        assert this.cameraFactory != null;
        return this.cameraFactory;
    }

    /**
     * Initialize the ServiceLocator.
     */
    private void init() {
        Res.register(this);
        FileSystem.register(this);
        Constants.register(this);
        LoggerFactory.register(this);

        AudioManager.register(this);
        EnemyFactory.register(this);
        InputManager.register(this);
        Calc.register(this);
        BlockFactory.register(this);
        DoodleFactory.register(this);
        PowerupFactory.register(this);
        SpriteFactory.register(this);
        Renderer.register(this);
        SceneFactory.register(this);
        PlatformFactory.register(this);
        ButtonFactory.register(this);
        ProgressionManager.register(this);
        MissionFactory.register(this);
        CameraFactory.register(this);
    }

}
