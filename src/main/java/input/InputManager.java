package input;

import logging.ILogger;
import system.IServiceLocator;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class manages the inputs given into the game.
 */
public final class InputManager implements IInputManager {

    /**
     * An empty list used as default for keyInputObservers.get().
     */
    private static final List<IKeyInputObserver> EMPTY_LIST = new ArrayList<>();

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the InputManager.
     */
    private final ILogger logger;
    /**
     * The set of observable mouse inputs.
     */
    private final HashSet<IMouseInputObserver> mouseInputObservers = new HashSet<>();
    /**
     * The set of observable key inputs.
     */
    private final Map<Keys, List<IKeyInputObserver>> keyInputObservers = new EnumMap<>(Keys.class);
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
        this.logger = serviceLocator.getLoggerFactory().createLogger(InputManager.class);
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        InputManager.serviceLocator = sL;
        InputManager.serviceLocator.provide(new InputManager());
    }

    /* MOUSE EVENTS */
    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mouseClicked(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mousePressed(final MouseEvent e) {
        int x = (2 * e.getX() - 2 * offsetX), y = (2 * e.getY() - 2 * offsetY);
        this.logger.info("Mouse pressed, button: " + e.getButton() + ", position: (" + x + "," + y + ")");

        Set<IMouseInputObserver> observers = (HashSet<IMouseInputObserver>) this.mouseInputObservers.clone();
        for (IMouseInputObserver observer : observers) {
            observer.mouseClicked(x, y);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mouseReleased(final MouseEvent e) {
        int x = (2 * e.getX() - 2 * offsetX), y = (2 * e.getY() - 2 * offsetY);
        this.logger.info("Mouse released, button: " + e.getButton() + ", position: (" + x + "," + y + ")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mouseEntered(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mouseExited(final MouseEvent e) {
    }

    /* KEY EVENTS */
    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void keyTyped(final KeyEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void keyPressed(final KeyEvent event) {
        this.logger.info("Key pressed, keyCode: " + event.getKeyCode());

        Keys key = KeyCode.getKey(event.getKeyCode());
        List<IKeyInputObserver> observers = this.keyInputObservers.getOrDefault(key, InputManager.EMPTY_LIST);
        System.out.println(observers.size() + " - " + key);
        for (IKeyInputObserver observer : observers) {
            observer.keyPress(KeyCode.getKey(event.getKeyCode()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void keyReleased(final KeyEvent event) {
        this.logger.info("Key released, keyCode: " + event.getKeyCode());

        Keys key = KeyCode.getKey(event.getKeyCode());
        List<IKeyInputObserver> observers = this.keyInputObservers.getOrDefault(key, InputManager.EMPTY_LIST);
        for (IKeyInputObserver observer : observers) {
            observer.keyRelease(KeyCode.getKey(event.getKeyCode()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addObserver(final IMouseInputObserver mouseInputObserver) {
        this.mouseInputObservers.add(mouseInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addObserver(final Keys key, final IKeyInputObserver keyInputObserver) {
        // We don't use InputManager.EMPTY_LIST here because it would alter that list.
        List<IKeyInputObserver> observers = this.keyInputObservers.getOrDefault(key, new ArrayList<>());
        observers.add(keyInputObserver);
        this.keyInputObservers.put(key, observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void removeObserver(final IMouseInputObserver mouseInputObserver) {
        this.mouseInputObservers.remove(mouseInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void removeObserver(final Keys key, final IKeyInputObserver keyInputObserver) {
        List<IKeyInputObserver> observers = this.keyInputObservers.getOrDefault(key, InputManager.EMPTY_LIST);
        observers.remove(keyInputObserver);
    }

    /**
     * Set the main border size, used for mouse inputs.
     * @param windowLBSize The size of the left border.
     * @param windowTBSize The size of the top border.
     */
    public synchronized void setMainWindowBorderSize(final int windowLBSize, final int windowTBSize) {
        this.offsetX = windowLBSize;
        this.offsetY = windowTBSize;
    }

}
