package objects.blocks.platform;

/**
 * Implemented by all classes that want to observe .
 */
public interface IPlatformObserver {
    /**
     * Called when a platform was hit.
     */
    void platformHit();
}
