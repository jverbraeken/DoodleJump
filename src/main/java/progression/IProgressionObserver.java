package progression;

/**
 * Implemented by classes that want a notification if the player progresses in the game.
 */
public interface IProgressionObserver {
    /**
     * @return The progression in observing the observable.
     */
    double getProgression();

    /**
     * @return The progression the observer must have before it's finished
     */
    int getMaximumTimes();

    /**
     * Resets the progression counter to 0.
     */
    void reset();

    /**
     * @param mission The mission the observer should be attached to
     */
    void setMission(final Mission mission);
}
