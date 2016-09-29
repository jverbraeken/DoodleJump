package input;

/**
 * This class is used to define a class that observes an {@link IInputManager} to get a signal when a key is pressed.
 */
public interface IKeyInputObserver {

    /**
     * This method is called when the user has pressed a key.
     *
     * @param key The key that is pressed.
     */
    void keyPress(final Keys key);

    /**
     * This method is called when the user has released a key.
     *
     * @param key The key that is released.
     */
    void keyRelease(final Keys key);

}
