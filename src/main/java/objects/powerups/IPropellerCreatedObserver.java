package objects.powerups;

/**
 * Implemented by all classes that want to receive a notification when a new {@link Propeller} was created.
 */
public interface IPropellerCreatedObserver {
    /**
     * Called when a new {@link Propeller} is created.
     *
     * @param propeller The new {@link Propeller} that was created
     */
    void alertPropellerCreated(final Propeller propeller);
}
