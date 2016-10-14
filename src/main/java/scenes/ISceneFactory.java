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
     * Create a new score screen.
     *
     * @return An IScene of the score screen.
     */
    IScene createScoreScreen();

    /**
     * Create a new world.
     *
     * @return The created world.
     */
    World createSinglePlayerWorld();

    /**
     * Create a new world for multiple players.
     *
     * @return The created world.
     */
    World createTwoPlayerWorld();

    /**
     * Create a choose mode screen.
     *
     * @return An IScene of the choose mode screen.
     */
    IScene newChooseMode();

}
