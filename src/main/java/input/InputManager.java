package input;

import system.IServiceLocator;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * This class manages the inputs given into the game.
 */
public final class InputManager implements IInputManager {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;
    /**
     * The size of the left border of the game frame.
     */
    private static int windowLeftBorderSize = 0;
    /**
     * The size of the top border of the game frame.
     */
    private static int windowTopBorderSize = 0;
    /**
     * The set of observable mouse inputs.
     */
    private final Set<IMouseInputObserver> mouseInputObservers = new HashSet<>();
    /**
     * The set of observable key inputs.
     */
    private final Set<IKeyInputObserver> keyInputObservers = new HashSet<>();

    /**
     * Prevents instantiation from outside the class.
     */
    private InputManager() {
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        InputManager.sL = sL;
        InputManager.sL.provide(new InputManager());
    }

    /* MOUSE EVENTS */

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(final MouseEvent e) {
        //TODO: Synchronize properly instead of cloning
        HashSet<IMouseInputObserver> observers = (HashSet) mouseInputObservers;
        observers = (HashSet) observers.clone();
        for (IMouseInputObserver observer : observers) {
            //observer.mouseClicked(e.getX() , e.getY() );
            observer.mouseClicked(2 * e.getX() - 2 * windowLeftBorderSize, 2 * e.getY() - 2 * windowTopBorderSize);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(final MouseEvent e) {
    }

    /* KEY EVENTS */

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(final KeyEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        for (IKeyInputObserver observer : keyInputObservers) {
            observer.keyPress(e.getKeyCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        for (IKeyInputObserver observer : keyInputObservers) {
            observer.keyRelease(e.getKeyCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final IMouseInputObserver mouseInputObserver) {
        mouseInputObservers.add(mouseInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final IKeyInputObserver keyInputObserver) {
        keyInputObservers.add(keyInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final IMouseInputObserver mouseInputObserver) {
        mouseInputObservers.remove(mouseInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final IKeyInputObserver keyInputObserver) {
        keyInputObservers.remove(keyInputObserver);
    }

    /**
     * Set the main border size, used for mouse inputs.
     *
     * @param windowLBSize The size of the left border.
     * @param windowTBSize The size of the top border.
     */
    public void setMainWindowBorderSize(final int windowLBSize, final int windowTBSize) {
        windowLeftBorderSize = windowLBSize;
        windowTopBorderSize = windowTBSize;
    }
}
