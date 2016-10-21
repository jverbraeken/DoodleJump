package input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * This class manages the user inputs given by mouse and keys.
 */
public interface IInputManager extends MouseListener, KeyListener {

    /**
     * Set the zie of the border of the main window.
     *
     * @param windowLeftBorderSize Size of the left border.
     * @param windowTopBorderSize Size of the top border.
     */
    void setMainWindowBorderSize(final int windowLeftBorderSize, final int windowTopBorderSize);

    // MOUSE
    /**
     * Adds a mouse to be Observed.
     *
     * @param mouseInputObserver the observer.
     */
    void addObserver(final IMouseInputObserver mouseInputObserver);

    /**
     * Removes a mouse to be Observed.
     *
     * @param mouseInputObserver the observer.
     */
    void removeObserver(final IMouseInputObserver mouseInputObserver);

    // KEYBOARD
    /**
     * Adds a key to be Observed.
     *
     * @param key The key from the Keys enum.
     * @param keyInputObserver the observer.
     */
    void addObserver(final Keys key, final IKeyInputObserver keyInputObserver);

    /**
     * Removes a key to be Observed.
     *
     * @param keyInputObserver the observer.
     */
    void removeObserver(final Keys key, final IKeyInputObserver keyInputObserver);

}
