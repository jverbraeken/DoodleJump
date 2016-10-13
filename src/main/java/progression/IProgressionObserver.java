package progression;

/**
 * Implemented by classes that want a notification if the player progresses in the game.
 */
public interface IProgressionObserver {
    /**
     * Of course we rather use notify, but as that name is occupied already by java.lang it's not possible
     * to use that name unfortunately.
     */
    void alert();

    /**
     * @return The progression in observing the observable.
     */
    int getProgression();
}
