package input;

import system.IServiceLocator;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public final class InputManager implements IInputManager {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new InputManager());
    }

    private final Set<IMouseInputObserver> mouseInputObservers = new HashSet<>();

    private InputManager() {

    }

    @Override
    /** {@inheritDoc} */
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (IMouseInputObserver observer : mouseInputObservers) {
            observer.mouseClicked(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    /** {@inheritDoc} */
    public void addObserver(IMouseInputObserver mouseInputObserver) {
        mouseInputObservers.add(mouseInputObserver);
    }

    @Override
    /** {@inheritDoc} */
    public void removeObserver(IMouseInputObserver mouseInputObserver) {
        mouseInputObservers.remove(mouseInputObserver);
    }
}
