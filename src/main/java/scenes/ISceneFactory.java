package scenes;

import system.IFactory;

/**
 * This class is a factory that creates scenes.
 */
public interface ISceneFactory extends IFactory {

    /**
     * Create a new kill screen.
     *
     * @param score the score reached by the preceding game.
     * @param extraExp the extra experience earned.
     * @return An IScene of the kill screen.
     */
    IScene createKillScreen(final int score, final int extraExp);

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

    /**
     * Create a shop.
     *
     * @return An IScene of the shop screen.
     */
    IScene createShopScreen();
}
