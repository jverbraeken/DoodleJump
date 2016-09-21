package input;

/**
 * This Class is called when the user interacts with a key.
 * Note: this method should only be called by an {@link IInputManager}.
 *
 */
public interface IKeyInputObserver {

    /**
     * This method is called when the user has pressed a key.
     * Note: this method should only be called by an {@link IInputManager}.
     *
     * @param keyCode The character that is pressed.
     */
    void keyPress(int keyCode);

    /**
     * This method is called when the user has released a key.
     * Note: this method should only be called by an {@link IInputManager}.
     *
     * @param keyCode The character that is released.
     */
    void keyRelease(int keyCode);

}
