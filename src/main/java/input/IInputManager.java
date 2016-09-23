package input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * This class manages the user inputs given by mouse and keys.
 */
public interface IInputManager extends MouseListener, KeyListener {

    /**
     * Set the zie of the border of the main window.
     * @param windowLeftBorderSize Size of the left border.
     * @param windowTopBorderSize Size of the top border.
     */
    void setMainWindowBorderSize(int windowLeftBorderSize, int windowTopBorderSize);

    // MOUSE

    /**
     * Adds a mouse to be Observed.
     * @param mouseInputObserver the mouse
     */
    void addObserver(IMouseInputObserver mouseInputObserver);

    /**
     * Removes a mouse to be Observed.
     * @param mouseInputObserver the mouse
     */
    void removeObserver(IMouseInputObserver mouseInputObserver);

    // KEYBOARD

    /**
     * Adds a key to be Observed.
     * @param keyInputObserver the key.
     */
    void addObserver(IKeyInputObserver keyInputObserver);

    /**
     * Removes a key to be Observed.
     * @param keyInputObserver the key.
     */
    void removeObserver(IKeyInputObserver keyInputObserver);
}
