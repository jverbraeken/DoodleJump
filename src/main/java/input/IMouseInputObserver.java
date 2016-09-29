package input;

/**
 * This class is used to define a class that observes an {@link IInputManager} to get a signal when a mouse
 * button is pressed.
 */
public interface IMouseInputObserver {

    /**
     * This method is called when the user has clicked with the mouse on the screen of the game.
     *
     * @param x The X-position of the mouse in pixels, as seen from the left
     * @param y The Y-position of the mouse in pixels, as seen from the top
     */
    void mouseClicked(final int x, final int y);

}
