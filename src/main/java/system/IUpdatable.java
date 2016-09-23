package system;

public interface IUpdatable {

    /**
     * Update the scene.
     * @param delta The time in milliseconds that has passed between the last frame and the new frame
     */
    void update(double delta);
}