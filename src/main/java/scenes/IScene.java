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

    /**
     * This method allows the game to change modes.
     */
    void switchMode(); // Most scenes do not use this method.

}
