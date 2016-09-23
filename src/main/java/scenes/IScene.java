package scenes;

/**
 * Interface for a scene.
 */
public interface IScene {

    /**
     * This method must be called when starting the scene.
     */
    void start();

    /**
     * This method must be called when stopping the scene.
     */
    void stop();

    /**
     * Repaint the scene.
     */
    void paint();

    /**
     * Update the scene.
     *
     * @param delta The time in milliseconds that has passed between the last frame and the new frame
     */
    void update(double delta);

}
