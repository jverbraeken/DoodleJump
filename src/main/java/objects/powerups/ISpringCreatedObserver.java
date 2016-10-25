package objects.powerups;

/**
 * Implemented by all classes that want to receive a notification when a new {@link Spring} was created.
 */
public interface ISpringCreatedObserver {
    /**
     * Called when a new {@link Spring} is created.
     *
     * @param spring The new {@link Spring} that was created
     */
    void alertSpringCreated(final Spring spring);
}
