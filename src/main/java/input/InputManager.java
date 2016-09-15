package input;

import system.IServiceLocator;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public final class InputManager implements IInputManager {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    private final Set<IMouseInputObserver> mouseInputObservers = new HashSet<>();
    private final Set<IKeyInputObserver> keyInputObservers = new HashSet<>();
    private int windowLeftBorderSize = 0;
    private int windowTopBorderSize = 0;

    /**
     * Prevents instantiation from outside the class.
     */
    private InputManager() {
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        InputManager.serviceLocator = serviceLocator;
        serviceLocator.provide(new InputManager());
    }

    /* MOUSE EVENTS */
    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (IMouseInputObserver observer : mouseInputObservers) {
            //observer.mouseClicked(e.getX() , e.getY() );
            observer.mouseClicked((2 * e.getX() - 2 * windowLeftBorderSize), (2 * e.getY() - 2 * windowTopBorderSize));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /* KEY EVENTS */

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        for (IKeyInputObserver observer : keyInputObservers) {
            observer.keyPress(e.getKeyCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(KeyEvent e) {
        for (IKeyInputObserver observer : keyInputObservers) {
            observer.keyRelease(e.getKeyCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(IMouseInputObserver mouseInputObserver) {
        mouseInputObservers.add(mouseInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(IKeyInputObserver keyInputObserver) {
        keyInputObservers.add(keyInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(IMouseInputObserver mouseInputObserver) {
        mouseInputObservers.remove(mouseInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(IKeyInputObserver keyInputObserver) {
        keyInputObservers.remove(keyInputObserver);
    }

    /**
     * Set the main border size, used for mouse inputs.
     *
     * @param windowLeftBorderSize The size of the left border.
     * @param windowTopBorderSize  The size of the top border.
     */
    public void setMainWindowBorderSize(int windowLeftBorderSize, int windowTopBorderSize) {
        this.windowLeftBorderSize = windowLeftBorderSize;
        this.windowTopBorderSize = windowTopBorderSize;
    }
}
