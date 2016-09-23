package input;

import logging.ILogger;
import system.IServiceLocator;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Standard implementation of the InputManager. Used to capture and notify observers about Key and Mouse input.
 */
public final class InputManager implements IInputManager {

    /**
     * The logger for the InputManager.
     */
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
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

    /**
     * Set of observers for the mouse.
     */
    private final Set<IMouseInputObserver> mouseInputObservers = new HashSet<>();
    /**
     * Set of observers for the keyboard.
     */
    private final Set<IKeyInputObserver> keyInputObservers = new HashSet<>();
    /**
     * Offset for the mouse position X.
     */
    private int offsetX = 0;
    /**
     * Offset for the mouse position Y.
     */
    private int offsetY = 0;

    /**
     * Prevents instantiation from outside the class.
     */
    private InputManager() {
        LOGGER = serviceLocator.getLoggerFactory().createLogger(InputManager.class);
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
        int x = (2 * e.getX() - 2 * offsetX), y = (2 * e.getY() - 2 * offsetY);
        LOGGER.info("Mouse pressed, button: " + e.getButton() + ", position: (" + x + "," + y + ")");

        //TODO: Synchronize properly instead of cloning
        HashSet<IMouseInputObserver> observers = (HashSet) mouseInputObservers;
        observers = (HashSet) observers.clone();
        for (IMouseInputObserver observer : observers) {
            observer.mouseClicked(x, y);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        int x = (2 * e.getX() - 2 * offsetX), y = (2 * e.getY() - 2 * offsetY);
        LOGGER.info("Mouse released, button: " + e.getButton() + ", position: (" + x + "," + y + ")");
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
        LOGGER.info("Key pressed, keyCode: " + e.getKeyCode());
        for (IKeyInputObserver observer : keyInputObservers) {
            observer.keyPress(e.getKeyCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(KeyEvent e) {
        LOGGER.info("Key released, keyCode: " + e.getKeyCode());
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
        this.offsetX = windowLeftBorderSize;
        this.offsetY = windowTopBorderSize;
    }

}
