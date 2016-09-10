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
    void paint(Graphics g);
    void update();
}
