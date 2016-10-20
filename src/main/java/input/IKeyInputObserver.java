package input;

import java.util.HashMap;

/**
 * This Class is called when the user interacts with a key.
 * Note: this method should only be called by an {@link IInputManager}.
 */
public interface IKeyInputObserver {

    /**
     * This method is called when the user has pressed a key.
     *
     * @param key The key that is pressed.
     */
    Runnable keyPress(final Keys key);

    /**
     * This method is called when the user has released a key.
     *
     * @param key The key that is released.
     */
    Runnable keyRelease(final Keys key);

}
