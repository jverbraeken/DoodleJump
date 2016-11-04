package resources.audio;

/**
 * Interface for an AudioManager.
 */
public interface IAudioManager {

    /**
     * Preload all sounds to reduce stuttering during the game.
     */
    void preload();

    /**
     * Play a sound specified one time.
     * @param sound The sound that should be played
     */
    void play(final Sounds sound);

    /**
     * Loop a sound over and over again.
     * @param sound The sound that should be looped
     */
    void loop(final Sounds sound);

    /**
     * Stops a sound from playing or looping.
     * @param sound The sound that should be stopped
     */
    void stop(final Sounds sound);
}
