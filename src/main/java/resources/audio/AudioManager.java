package resources.audio;

import system.IServiceLocator;

/**
 * Standard implementation of the AudioManager. Used to load an play audio.
 * <br>
 * It is not deemed necessary for all individual sounds to have a JavaDoc.
 */
public final class AudioManager implements IAudioManager {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        AudioManager.serviceLocator = sL;
        AudioManager.serviceLocator.provide(new AudioManager());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private AudioManager() {
        this.preload();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preload() {
        Sounds.preload(AudioManager.serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play(final Sounds sound) {
        sound.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loop(final Sounds sound) {
        sound.loop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final Sounds sound) {
        sound.stop();
    }

}
