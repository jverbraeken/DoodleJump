package buttons;

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
    IButton createPlayButton(final int x, final int y);

    /**
     * Create a press to start playing button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a play again button.
     */
    IButton createPlayAgainButton(final int x, final int y);

    /**
     * Create a press to resume playing button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a resume button.
     */
    IButton createResumeButton(final int x, final int y);

    /**
     * Create a press to go to main menu button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a main menu button.
     */
    IButton createMainMenuButton(final int x, final int y);

    /**
     * Create a press to go to choose mode button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a choose mode button.
     */
    IButton createChooseModeButton(final int x, final int y);

    /**
     * Create a press to go set the mode to regular.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a regular mode button.
     */
    IButton createRegularModeButton(final int x, final int y);

    /**
     * Create a press to go set the mode to darkness.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a darkness mode button.
     */
    IButton createDarknessModeButton(final int x, final int y);

    /**
     * Create a press to go set the mode to invert.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return an invert mode button.
     */
    IButton createInvertModeButton(final int x, final int y);

    /**
     * Create a press to go set the mode to underwater.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return an underwater mode button.
     */
    IButton createUnderwaterModeButton(final int x, final int y);

    /**
     * Create a press to go set the mode to space.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a space mode button.
     */
    IButton createSpaceModeButton(final int x, final int y);

    /**
     * Create a press to go set the mode to story.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a story mode button.
     */
    IButton createStoryModeButton(final int x, final int y);

}
