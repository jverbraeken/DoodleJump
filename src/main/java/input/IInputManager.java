package input;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public interface IInputManager extends MouseListener, KeyListener {

    void addObserver(IMouseInputObserver mouseInputObserver);
    void addObserver(IKeyInputObserver keyInputObserver);

    void removeObserver(IMouseInputObserver mouseInputObserver);
    void removeObserver(IKeyInputObserver keyInputObserver);

}
