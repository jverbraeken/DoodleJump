package input;

import java.awt.event.KeyEvent;

public interface IKeyInputObserver {

    /**
     * This method is called when the user has clicked with the mouse on the screen of the game.
     * </br>
     * Note: this method should only be called by an {@link IInputManager}.
     * </br>
     * @param keyCode The character that is pressed.
     */
    void keyPressed(int keyCode);

}
