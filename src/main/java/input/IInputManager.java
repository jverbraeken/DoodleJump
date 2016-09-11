package input;

import java.awt.event.MouseListener;

public interface IInputManager extends MouseListener {
    void addObserver(IMouseInputObserver mouseInputObserver);
    void removeObserver(IMouseInputObserver mouseInputObserver);
}
