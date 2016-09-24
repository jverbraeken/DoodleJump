package scenes;

import system.IFactory;

/**
 * This class is a factory that creates scenes.
 */
public interface ISceneFactory extends IFactory {
    /**
     * Create a new kill screen.
     *
     * @return An IScene of the kill screen.
     */
    IScene createKillScreen();
    /**
     * Create a new main menu.
     *
     * @return An IScene of the main menu.
     */
    IScene createMainMenu();

    /**
     * Create a new pause screen.
     *
     * @return An IScene of the pause screen.
     */
    IScene createPauseScreen();

    /**
     * Create a new world.
     *
     * @return The created world.
     */
    World newWorld();

}
