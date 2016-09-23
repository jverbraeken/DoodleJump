package scenes;

import system.IFactory;

/**
 * This class is a factory that creates scenes.
 */
public interface ISceneFactory extends IFactory {
    /**
     * Create a new main menu.
     * @return a main menu
     */
    IScene newMenu();

    /**
     * Create a new world.
     * @return a world.
     */
    IScene newWorld();

    /**
     * Create a new kill screen.
     * @return a kill screen
     */
    IScene newKillScreen();

}
