package input;

import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class InputManagerTest {

    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    MouseEvent mouseEvent = mock(MouseEvent.class);
    KeyEvent keyEventArrowLeft = mock(KeyEvent.class);

    IInputManager inputManager;

    HashSet<IMouseInputObserver> mouseObservers = new HashSet<>();
    IMouseInputObserver mouseObserver = mock(IMouseInputObserver.class);
    Map<Keys, List<IKeyInputObserver>> keyObservers = new EnumMap<>(Keys.class);
    List<IKeyInputObserver> keyObserverArrowLeftList = new ArrayList<>();
    IKeyInputObserver keyObserver = mock(IKeyInputObserver.class);

    int x = 10;
    int y = 11;

    @Before
    public void initialize() throws Exception {
        when(loggerFactory.createLogger(InputManager.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(mouseEvent.getX()).thenReturn(x);
        when(mouseEvent.getY()).thenReturn(y);
        when(keyEventArrowLeft.getKeyCode()).thenReturn(KeyCode.getKeyCode(Keys.arrowLeft));

        Whitebox.setInternalState(InputManager.class, "serviceLocator", serviceLocator);
        inputManager = Whitebox.invokeConstructor(InputManager.class);

        mouseObservers.add(mouseObserver);
        keyObserverArrowLeftList.add(keyObserver);
        keyObservers.put(Keys.arrowLeft, keyObserverArrowLeftList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegister() {
        InputManager.register(null);
    }

    @Test
    public void testMousePressedLogs() {
        inputManager.mousePressed(mouseEvent);
        verify(logger, times(1)).info(anyString());
    }

    @Test
    public void testMousePressedNotifyObservers() {
        Whitebox.setInternalState(inputManager, "mouseInputObservers", mouseObservers);
        inputManager.mousePressed(mouseEvent);
        verify(mouseObserver, times(1)).mouseClicked(2 * x, 2 * y);
    }

    @Test
    public void testMouseReleasedLogs() {
        inputManager.mouseReleased(mouseEvent);
        verify(logger, times(1)).info(anyString());
    }

    @Test
    public void testKeyPressedLogs() {
        inputManager.keyPressed(keyEventArrowLeft);
        verify(logger, times(1)).info(anyString());
    }

    @Test
    public void testKeyPressedNotifyObservers() {
        Whitebox.setInternalState(inputManager, "keyInputObservers", keyObservers);
        inputManager.keyPressed(keyEventArrowLeft);
        verify(keyObserver, times(1)).keyPress(Keys.arrowLeft);
    }

    @Test
    public void testReleasedPressedLogs() {
        inputManager.keyReleased(keyEventArrowLeft);
        verify(logger, times(1)).info(anyString());
    }

    @Test
    public void testReleasedPressedNotifyObservers() {
        Whitebox.setInternalState(inputManager, "keyInputObservers", keyObservers);
        inputManager.keyReleased(keyEventArrowLeft);
        verify(keyObserver, times(1)).keyRelease(Keys.arrowLeft);
    }

    @Test
    public void testAddMouseObserver() {
        inputManager.addObserver(mouseObserver);
        HashSet<IMouseInputObserver> mouseInputObservers = Whitebox.getInternalState(inputManager, "mouseInputObservers");
        assertThat(mouseInputObservers, is(mouseObservers));
    }

    @Test
    public void testAddKeyObserver() {
        inputManager.addObserver(Keys.arrowLeft, keyObserver);
        Map<Keys, List<IKeyInputObserver>> keyInputObservers = Whitebox.getInternalState(inputManager, "keyInputObservers");
        assertThat(keyInputObservers, is(keyObservers));
    }

    @Test
    public void testRemoveMouseObserver() {
        Whitebox.setInternalState(inputManager, "mouseInputObservers", mouseObservers);
        inputManager.removeObserver(mouseObserver);

        HashSet<IMouseInputObserver> mouseInputObservers = Whitebox.getInternalState(inputManager, "mouseInputObservers");
        mouseObservers.remove(mouseObserver);

        assertThat(mouseInputObservers, is(mouseObservers));
    }

    @Test
    public void testRemoveKeyObserver() {
        Whitebox.setInternalState(inputManager, "keyInputObservers", keyObservers);
        inputManager.removeObserver(Keys.arrowLeft, keyObserver);

        Map<Keys, List<IKeyInputObserver>> keyInputObservers = Whitebox.getInternalState(inputManager, "keyInputObservers");
        Map<Keys, List<IKeyInputObserver>> expected = new EnumMap<>(Keys.class);
        keyObserverArrowLeftList.remove(keyObserver);
        expected.put(Keys.arrowLeft, keyObserverArrowLeftList);

        assertThat(keyInputObservers, is(expected));
    }

    @Test
    public void testSetMainWindowBorderSize() {
        inputManager.setMainWindowBorderSize(100, 100);

        int x = Whitebox.getInternalState(inputManager, "offsetX");
        assertThat(x, is(100));

        int y = Whitebox.getInternalState(inputManager, "offsetY");
        assertThat(y, is(100));
    }

    @Test
    public void testMouseClicked() {
        inputManager.mouseClicked(mouseEvent);
        assertThat(true, is(true)); // No crash
    }

    @Test
    public void testMouseEntered() {
        inputManager.mouseEntered(mouseEvent);
        assertThat(true, is(true)); // No crash
    }

    @Test
    public void testMouseExited() {
        inputManager.mouseExited(mouseEvent);
        assertThat(true, is(true)); // No crash
    }

    @Test
    public void testKeyTyped() {
        inputManager.keyTyped(keyEventArrowLeft);
        assertThat(true, is(true)); // No crash
    }

}
