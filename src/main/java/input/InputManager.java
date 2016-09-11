package input;

import system.IServiceLocator;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public final class InputManager implements IInputManager {
    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;
    /**
    * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
    * @param serviceLocator The IServiceLocator to which the class should offer its functionality
    */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        InputManager.serviceLocator = serviceLocator;
        serviceLocator.provide(new InputManager());
    }

    private final Set<IMouseInputObserver> mouseInputObservers = new HashSet<>();

    /**
     * Prevents instantiation from outside the class.
     */
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
