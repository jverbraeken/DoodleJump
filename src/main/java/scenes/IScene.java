package scenes;

import java.awt.*;

/**
 * Created by joost on 6-9-16.
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
     * @param g The graphics buffer to which the scene should be painted
     */
    void paint(Graphics g);

    /**
     * Update the scene.
     * @param delta The time in milliseconds that has passed between the last frame and the new frame
     */
    void update(double delta);
}
