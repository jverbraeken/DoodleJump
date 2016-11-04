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

    /**
     * Update the buttons in the Scene.
     *
     * @param powerup   The powerup the button is related to.
     * @param x         The X position of the button.
     * @param y         The Y position of the button.
     */
    default void updateButton(final Powerups powerup, final double x, final double y) {
        // Shouldn't do anything in most scenes.
    }

    /**
     * Switch the display of the Scene.
     *
     * @param mode The mode to switch to.
     */
    default void switchDisplay(final PauseScreenModes mode) {
        // Shouldn't do anything in most scenes.
    }

}
