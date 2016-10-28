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
     * This method allows the game to change the display of a scene.
     *
     * @param mode The mode of the display
     */
    void switchDisplay(PauseScreenModes mode); // Most scenes do not use this method.

    /**
     * This method replaces the button of a powerup.
     *
     * @param powerup The type of powerup
     * @param x The x position of the button
     * @param y The y position of the button
     */
    void updateButton(final Powerups powerup, final double x, final double y);

}
