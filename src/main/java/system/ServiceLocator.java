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
/* package */ final class ServiceLocator extends ServiceLocatorNoAudio implements IServiceLocator {

    // audio
    private IAudioManager audioManager;

    /**
     * The singleton serviceLocator.
     * Constructed eagerly.
     */
    private static final IServiceLocator SERVICE_LOCATOR = new ServiceLocator();

    /**
     * Initialize the ServiceLocator class.
     */
    private ServiceLocator() {
        super();
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
    public IAudioManager getAudioManager() {
        assert audioManager != null;
        return audioManager;
    }

    /**
     * Initialize the ServiceLocator.
     */
    private void init() {
        AudioManager.register(this);
    }

}
