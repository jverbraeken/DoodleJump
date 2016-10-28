package objects.powerups;

/**
 * Implemented by all classes that want to receive a notification when a new {@link Jetpack} was created.
 */
public interface IJetpackCreatedObserver {
    /**
     * Called when a new {@link Jetpack} is created.
     *
     * @param jetpack The new {@link Jetpack} that was created
     */
    void alertJetpackCreated(final Jetpack jetpack);
}
