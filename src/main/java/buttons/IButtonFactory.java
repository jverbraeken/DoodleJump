package buttons;

import objects.powerups.Powerups;
import system.IFactory;

/**
 * Interface for a ButtonFactory.
 */
public interface IButtonFactory extends IFactory {

    /**
     * Create a press to start playing button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a play button.
     */
    IButton createPlayButton(final double x, final double y);

    /**
     * Create a press to start playing again button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a play again button.
     */
    IButton createPlayAgainButton(final double x, final double y);

    /**
     * Create a press to start multiplayer playing button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a play again button.
     */
    IButton createMultiplayerButton(final double x, final double y);

    /**
     * Create a new button that opens the shop when clicked.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a shop button.
     */
    IButton createShopButton(final double x, final double y);

    /**
     * Create a press to resume playing button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a resume button.
     */
    IButton createResumeButton(final double x, final double y);

    /**
     * Create a press to go to main menu button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a main menu button.
     */
    IButton createMainMenuButton(final double x, final double y);

    /**
     * Create a button to go to the high scores list.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a score button.
     */
    IButton createScoreButton(final double x, final double y);

    /**
     * Create a press to go to choose mode button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a choose mode button.
     */
    IButton createChooseModeButton(final double x, final double y);

    /**
     * Create a press to go set the mode to regular.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a regular mode button.
     */
    IButton createRegularModeButton(final double x, final double y);

    /**
     * Create a press to go set the mode to darkness.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a darkness mode button.
     */
    IButton createDarknessModeButton(final double x, final double y);

    /**
     * Create a press to go set the mode to verticalOnly.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return an verticalOnly mode button.
     */
    IButton createInvertModeButton(final double x, final double y);

    /**
     * Create a press to go set the mode to underwater.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return an underwater mode button.
     */
    IButton createUnderwaterModeButton(final double x, final double y);

    /**
     * Create a press to go set the mode to space.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a space mode button.
     */
    IButton createSpaceModeButton(final double x, final double y);

    /**
     * Create a press to go set the mode to horizontalOnly.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a horizontalOnly mode button.
     */
    IButton createStoryModeButton(final double x, final double y);

    /**
     * Creates and retuns an Ok button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @param popup the Popup this button belongs to.
     * @return an Ok button.
     */
    IButton createOkPopupButton(final double x, final double y, final scenes.Popup popup);


    /////   SHOP


    /**
     * Create a button to upgrade the {@link objects.powerups.Jetpack} powerup.
     *
     * @param powerup The kind of powerup you want to create an upgrade button for
     * @param x       the x position of the button relative to the screen
     * @param y       the y position of the button relative to the screen
     * @param height  The height of the button
     * @return A button that can upgrade the {@link objects.powerups.Jetpack} powerup
     */
    IButton createShopPowerupButton(final Powerups powerup, final double x, final double y, final int height);

    /**
     * Create a button to upgrade the {@link objects.powerups.Jetpack} powerup.
     *
     * @param powerup The kind of powerup you want to create an upgrade button for
     * @param x       the x position of the button relative to the screen
     * @param y       the y position of the button relative to the screen
     * @return A button that can upgrade the {@link objects.powerups.Jetpack} powerup
     */
    IButton createPausePowerupButton(final Powerups powerup, final double x, final double y);

    /**
     * Create a pause button for in game.
     *
     * @param x The x position of the button.
     * @param y The y position of the button.
     * @return A pause button.
     */
    IButton createPauseButton(final double x, final double y);

    /**
     * Create a switch button that switches the screen to the display of shop in the pause screen.
     *
     * @param x The x position of the button.
     * @param y The y position of the button.
     * @return A switch button.
     */
    IButton createSwitchToShopButton(final double x, final double y);

    /**
     * Create a switch button that switches the screen to the display of shop in the pause screen.
     *
     * @param x The x position of the button.
     * @param y The y position of the button.
     * @return A switch button.
     */
    IButton createSwitchToMissionButton(final double x, final double y);
}
