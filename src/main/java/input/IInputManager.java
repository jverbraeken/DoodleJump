package input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public interface IInputManager extends MouseListener, KeyListener {

    void setMainWindowBorderSize(int windowLeftBorderSize, int windowTopBorderSize);

    // MOUSE

    void addObserver(IMouseInputObserver mouseInputObserver);
    void removeObserver(IMouseInputObserver mouseInputObserver);

    // KEYBOARD

    void addObserver(IKeyInputObserver keyInputObserver);
    void removeObserver(IKeyInputObserver keyInputObserver);
}
