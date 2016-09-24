package input;

/**
 * This method is called when the user interacts with the mouse.
 * Note: this method should only be called by an {@link IInputManager}.
 *
 */
public interface IMouseInputObserver {

    /**
     * This method is called when the user has clicked with the mouse on the screen of the game.
     * Note: this method should only be called by an {@link IInputManager}.
     *
     * @param x The X-position of the mouse in pixels, as seen from the left
     * @param y The Y-position of the mouse in pixels, as seen from the top
     */
    void mouseClicked(int x, int y);

}
