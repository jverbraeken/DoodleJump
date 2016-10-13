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
     * Of course we rather use notify, but as that name is occupied already by java.lang it's not possible
     * to use that name unfortunately.
     *
     * @param amount The amount of which the variable that caused the trigger to alert the observers has been changed.
     */
    void alert(final double amount);

    /**
     * @return The progression in observing the observable.
     */
    double getProgression();

    /**
     * Resets the progression counter to 0.
     */
    void reset();

    /**
     * @param mission The mission the observer should be attached to
     */
    void setMission(final Mission mission);
}
