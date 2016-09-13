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

    private static final Set<IMouseInputObserver> mouseInputObservers = new HashSet<>();
    private int windowLeftBorderSize = 0;
    private int windowTopBorderSize = 0;

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
            observer.mouseClicked(e.getX() - windowLeftBorderSize, e.getY() - windowTopBorderSize);
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

    @Override
    /** {@inheritDoc} */
    public void setMainWindowBorderSize(int windowLeftBorderSize, int windowTopBorderSize) {
        this.windowLeftBorderSize = windowLeftBorderSize;
        this.windowTopBorderSize = windowTopBorderSize;
    }
}
