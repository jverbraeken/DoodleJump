package input;

import logging.ILogger;
import system.IServiceLocator;

import javax.swing.text.html.HTMLDocument;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class manages the inputs given into the game.
 */
public final class InputManager implements IInputManager {

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
    //private final HashSet<IKeyInputObserver> keyInputObservers = new HashSet<>();
    private final HashMap<Integer, IKeyInputObserver> keyInputObservers = new HashMap<>();
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
    public void mouseClicked(final MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(final MouseEvent e) {
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
    public void mouseReleased(final MouseEvent e) {
        int x = (2 * e.getX() - 2 * offsetX), y = (2 * e.getY() - 2 * offsetY);
        this.logger.info("Mouse released, button: " + e.getButton() + ", position: (" + x + "," + y + ")");
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
    public void keyPressed(final KeyEvent event) {
        this.logger.info("Key pressed, keyCode: " + event.getKeyCode());
        Iterator it = this.keyInputObservers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if ((int) pair.getKey() == event.getKeyCode()) {
                IKeyInputObserver observer = (IKeyInputObserver) pair.getValue();
                Runnable runnable = observer.keyPress(KeyCode.getKey(event.getKeyCode()));
                try {
                    runnable.run();
                } catch (Exception e) {
                    logger.warning("runnable not found");
                }
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        this.logger.info("Key released, keyCode: " + e.getKeyCode());
        Iterator it = this.keyInputObservers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if ((int) pair.getKey() == e.getKeyCode()) {
                IKeyInputObserver observer = (IKeyInputObserver) pair.getValue();
                Runnable runnable = observer.keyRelease(KeyCode.getKey(e.getKeyCode()));
                runnable.run();
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final IMouseInputObserver mouseInputObserver) {
        this.mouseInputObservers.add(mouseInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final Keys key, final IKeyInputObserver keyInputObserver) {
        this.keyInputObservers.put(KeyCode.getKeyCode(key), keyInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final IMouseInputObserver mouseInputObserver) {
        this.mouseInputObservers.remove(mouseInputObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final IKeyInputObserver keyInputObserver) {
        Iterator it = this.keyInputObservers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals(keyInputObserver)) {
                this.keyInputObservers.remove(pair.getKey(), pair.getValue());
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     * Set the main border size, used for mouse inputs.
     * @param windowLBSize The size of the left border.
     * @param windowTBSize The size of the top border.
     */
    public void setMainWindowBorderSize(final int windowLBSize, final int windowTBSize) {
        this.offsetX = windowLBSize;
        this.offsetY = windowTBSize;
    }

}
