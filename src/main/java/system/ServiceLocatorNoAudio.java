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
import objects.doodles.Projectiles.IProjectileFactory;
import objects.doodles.Projectiles.ProjectileFactory;
import objects.enemies.EnemyFactory;
import objects.enemies.IEnemyFactory;
import objects.powerups.IPowerupFactory;
import objects.powerups.PowerupFactory;
import rendering.CameraFactory;
import rendering.ICameraFactory;
import rendering.IRenderer;
import rendering.Renderer;
import resources.IRes;
import resources.Res;
import resources.audio.IAudioManager;
import resources.sprites.ISpriteFactory;
import resources.sprites.SpriteFactory;
import scenes.ISceneFactory;
import scenes.SceneFactory;

/**
 * Default implementation for the ServiceLocatorNoAudio. Used to gain access to all services.
 * The difference between this ServiceLcoator and the standard one is the absence of Audio services.
 */
@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
/* package */ class ServiceLocatorNoAudio implements IServiceLocator {

    // constants.json
    private IConstants constants;

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

    // objects
    private IPowerupFactory powerupFactory;
    private IDoodleFactory doodleFactory;
    private IBlockFactory blockFactory;
    private IPlatformFactory platformFactory;
    private IProjectileFactory projectileFactory;

    // resources
    private ISpriteFactory spriteFactory;
    private IRes res;

    // scenes
    private ISceneFactory sceneFactory;

    // utility
    private ICalc calc;

    /**
     * The serviceLocator.
     */
    private static final ServiceLocatorNoAudio SERVICE_LOCATOR = new ServiceLocatorNoAudio();

    /**
     * Initialize the ServiceLocator class.
     */
    protected ServiceLocatorNoAudio() {
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
    @SuppressWarnings("methodlength")
    public IAudioManager getAudioManager() {
        return new IAudioManager() {
            @Override
            public void preload() {
            }

            @Override
            public void playBijeli() {

            }

            @Override
            public void playBlizzard() {

            }

            @Override
            public void playBubbles1() {

            }

            @Override
            public void playBubbles2() {

            }

            @Override
            public void playChill() {

            }

            @Override
            public void playCollect() {

            }

            @Override
            public void playCrnarupa() {

            }

            @Override
            public void playEggMonsterHit() {

            }

            @Override
            public void playExplodingPlatform() {

            }

            @Override
            public void playExplodingPlatform2() {

            }

            @Override
            public void playFeder() {

            }

            @Override
            public void playJetpack() {

            }

            @Override
            public void playJump() {

            }

            @Override
            public void playJumpOnMonster() {

            }

            @Override
            public void playLomise() {

            }

            @Override
            public void playMatchSound() {

            }

            @Override
            public void playMonsterCrash() {

            }

            @Override
            public void playMonsterBlizu() {

            }

            @Override
            public void playMonsterPogodak() {

            }

            @Override
            public void playOogapucanje() {

            }

            @Override
            public void playOogapucanje2() {

            }

            @Override
            public void playPada() {

            }

            @Override
            public void playPropeller() {

            }

            @Override
            public void playPucanje() {

            }

            @Override
            public void playPucanje2() {

            }

            @Override
            public void playRain() {

            }

            @Override
            public void playRocket() {

            }

            @Override
            public void playSnowballMonsterHit() {

            }

            @Override
            public void playSnowballThrow() {

            }

            @Override
            public void playSnowballThrow2() {

            }

            @Override
            public void playSoccerMonsterCrash() {

            }

            @Override
            public void playSoccerMonsterHit() {

            }

            @Override
            public void playSpringShoes() {

            }

            @Override
            public void playStart() {

            }

            @Override
            public void playThunder() {

            }

            @Override
            public void playTrampoline() {

            }

            @Override
            public void playUFO() {

            }

            @Override
            public void playUFOPogodak() {

            }

            @Override
            public void playUnderwaterShoot() {

            }

            @Override
            public void playUnderwaterShoot2() {

            }

            @Override
            public void playUsaugateufo() {

            }

            @Override
            public void playWin() {

            }
        };
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
     * Initialize the ServiceLocator.
     */
    private void init() {
        Res.register(this);
        FileSystem.register(this);
        Constants.register(this);
        LoggerFactory.register(this);

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
    }

}
