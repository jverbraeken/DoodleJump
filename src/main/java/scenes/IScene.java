package scenes;

import objects.powerups.Powerups;
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
     * This method must be called to register the scene.
     */
    void register();

    /**
     * This method must be called to deregister the scene.
     */
    void deregister();

}
