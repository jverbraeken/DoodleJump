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
    IButton createPlayButton(int x, int y);

    /**
     * Create a press to start playing button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a play again button.
     */
    IButton createPlayAgainButton(int x, int y);

    /**
     * Create a press to resume playing button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a resume button.
     */
    IButton createResumeButton(int x, int y);

    /**
     * Create a press to go to main menu button.
     *
     * @param x the x position of the button.
     * @param y the y position of the button.
     * @return a main menu button.
     */
    IButton createMainMenuButton(int x, int y);

}
