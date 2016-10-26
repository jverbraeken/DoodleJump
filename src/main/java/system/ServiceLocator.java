package system;

import resources.audio.AudioManager;
import resources.audio.IAudioManager;

/**
 * Default implementation for the ServiceLocator. Used to gain access to all services.
 */
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
