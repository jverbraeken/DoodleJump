package input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Interface for an InputManager.
 */
public interface IInputManager extends MouseListener, KeyListener {

    void setMainWindowBorderSize(int windowLeftBorderSize, int windowTopBorderSize);

    // MOUSE
    /**
     * Add a mouse input observer.
     *
     * @param mouseInputObserver The observing object.
     */
    void addObserver(IMouseInputObserver mouseInputObserver);

    /**
     * Remove a mouse input observer.
     *
     * @param mouseInputObserver The observing object.
     */
    void removeObserver(IMouseInputObserver mouseInputObserver);

    // KEYBOARD
    /**
     * Add a key input observer.
     *
     * @param keyInputObserver The observing object.
     */
    void addObserver(IKeyInputObserver keyInputObserver);

    /**
     * Remove a key input observer.
     *
     * @param keyInputObserver The observing object.
     */
    void removeObserver(IKeyInputObserver keyInputObserver);
}
