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
import objects.doodles.projectiles.IProjectileFactory;
import objects.doodles.projectiles.ProjectileFactory;
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
import resources.animations.AnimationFactory;
import resources.animations.IAnimationFactory;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
import scenes.ISceneFactory;
import scenes.SceneFactory;

/**
 * Default implementation for the ServiceLocatorNoAudio. Used to gain access to all services.
 * The difference between this ServiceLocator and the standard one is the absence of Audio services.
 */
/* package */ class ServiceLocatorNoAudio implements IServiceLocator {

    // filesystem
    private IFileSystem fileSystem;

    // input
    private IInputManager inputManager;

    // objects
    private IBlockFactory blockFactory;
    private IDoodleFactory doodleFactory;
    private IEnemyFactory enemyFactory;
    private IPlatformFactory platformFactory;
    private IPowerupFactory powerupFactory;
    private IProjectileFactory projectileFactory;

    // progression
    private IMissionFactory missionFactory;
    private IProgressionManager progressionManager;

    // rendering
    private ICameraFactory cameraFactory;
    private IRenderer renderer;

    // resources
    private IAnimationFactory animationFactory;
    private IRes res;
    private ISpriteFactory spriteFactory;

    // scenes
    private ISceneFactory sceneFactory;

    // utility
    private IButtonFactory buttonFactory;
    private ICalc calc;
    private IConstants constants;
    private ILoggerFactory loggerFactory;

    /**
     * The serviceLocator.
     */
    private static final ServiceLocatorNoAudio SERVICE_LOCATOR = new ServiceLocatorNoAudio();

    /**
     * Initialize the ServiceLocator class.
     */
    /* package */ ServiceLocatorNoAudio() {
        this.init();
    }

    /**
     * Getter of the singleton service locator.
     *
     * @return the service locator.
     */
    public static IServiceLocator getServiceLocator() {
        return ServiceLocatorNoAudio.SERVICE_LOCATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provide(final IAudioManager aM) {
        // Shouldn't do anything, as this is a ServiceLocator without an AudioManager
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
    public void provide(final IProjectileFactory pF) {
        assert pF != null;
        this.projectileFactory = pF;
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
    public void provide(final IAnimationFactory animationFactory) {
        assert animationFactory != null;
        this.animationFactory = animationFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAudioManager getAudioManager() {
        // Returns a fake AudioManager that doesn't do anything.
        return new IAudioManager() {
            @Override
            public void preload() {
            }

            @Override
            public void play(AudioManager.Sound sound) {
            }

            @Override
            public void loop(AudioManager.Sound sound) {
            }

            @Override
            public void stop(AudioManager.Sound sound) {
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEnemyFactory getEnemyFactory() {
        assert this.enemyFactory != null;
        return this.enemyFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IFileSystem getFileSystem() {
        assert this.fileSystem != null;
        return this.fileSystem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IInputManager getInputManager() {
        assert this.inputManager != null;
        return this.inputManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICalc getCalc() {
        assert this.calc != null;
        return this.calc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBlockFactory getBlockFactory() {
        assert this.blockFactory != null;
        return this.blockFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodleFactory getDoodleFactory() {
        assert this.doodleFactory != null;
        return this.doodleFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPowerupFactory getPowerupFactory() {
        assert this.powerupFactory != null;
        return this.powerupFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRenderer getRenderer() {
        assert this.renderer != null;
        return this.renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISpriteFactory getSpriteFactory() {
        assert this.spriteFactory != null;
        return this.spriteFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISceneFactory getSceneFactory() {
        assert this.sceneFactory != null;
        return this.sceneFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatformFactory getPlatformFactory() {
        assert this.platformFactory != null;
        return this.platformFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRes getRes() {
        assert this.res != null;
        return this.res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButtonFactory getButtonFactory() {
        assert this.buttonFactory != null;
        return this.buttonFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IConstants getConstants() {
        assert this.constants != null;
        return this.constants;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILoggerFactory getLoggerFactory() {
        assert this.loggerFactory != null;
        return this.loggerFactory;
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
     * {@inheritDoc}
     */
    @Override
    public IProjectileFactory getProjectileFactory() {
        assert this.projectileFactory != null;
        return this.projectileFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IProgressionManager getProgressionManager() {
        assert this.progressionManager != null;
        return this.progressionManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IMissionFactory getMissionFactory() {
        assert this.missionFactory != null;
        return this.missionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAnimationFactory getAnimationFactory() {
        assert this.animationFactory != null;
        return this.animationFactory;
    }

    /**
     * Initialize the ServiceLocator.
     */
    private void init() {
        // Need to be registered first
        Res.register(this);
        FileSystem.register(this);
        Constants.register(this);
        LoggerFactory.register(this);

        // Registering order does not matter here
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
        CameraFactory.register(this);
        ProjectileFactory.register(this);
        ProgressionManager.register(this);
        MissionFactory.register(this);
        AnimationFactory.register(this);
    }

}
