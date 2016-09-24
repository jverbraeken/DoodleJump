package scenes;

import system.IRenderable;
import system.IUpdatable;

/**
 * Interface for a scene.
 */
public interface IScene extends IUpdatable, IRenderable {

    /**
     * This method must be called when starting the scene.
     */
    void start();

    /**
     * This method must be called when stopping the scene.
     */
    void stop();
}
