package system;

/**
 * Interface for objects that should be updatable.
 */
public interface IUpdatable {

    /**
     * Update the scene.
     *
     * @param delta The time in milliseconds that has passed between the last frame and the new frame
     */
    void update(final double delta);

}
