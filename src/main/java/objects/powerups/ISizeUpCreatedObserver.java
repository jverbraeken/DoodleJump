package objects.powerups;

/**
 * Implemented by all classes that want to receive a notification when a new {@link SizeUp} was created.
 */
public interface ISizeUpCreatedObserver {
    /**
     * Called when a new {@link SizeUp} is created.
     *
     * @param sizeUp The new {@link SizeUp} that was created
     */
    void alertSizeUpCreated(final SizeUp sizeUp);
}
