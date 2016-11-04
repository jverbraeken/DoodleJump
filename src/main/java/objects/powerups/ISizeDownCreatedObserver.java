package objects.powerups;

/**
 * Implemented by all classes that want to receive a notification when a new {@link SizeDown} was created.
 */
public interface ISizeDownCreatedObserver {
    /**
     * Called when a new {@link SizeDown} is created.
     *
     * @param sizeDown The new {@link SizeDown} that was created
     */
    void alertSizeDownCreated(final SizeDown sizeDown);
}
