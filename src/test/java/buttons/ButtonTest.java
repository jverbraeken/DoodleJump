package buttons;

import groovy.lang.Tuple2;
import input.InputManager;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

import java.awt.Image;
import java.awt.Point;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test suite for the Button.java class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({InputManager.class, Game.class})
public class ButtonTest {

    private static InputManager inputManager = mock(InputManager.class);
    private static ILogger logger = mock(ILogger.class);
    private static ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private static IServiceLocator serviceLocator = mock(IServiceLocator.class);

    private Game game = mock(Game.class);
    private Tuple2<Integer, Integer> dimensions = new Tuple2<>(30, 20);
    private IButton button, button2;
    private Image image = mock(Image.class);
    private IRenderer renderer = mock(IRenderer.class);
    private ISprite sprite = mock(ISprite.class);
    private Runnable action = mock(Runnable.class);
    private String buttonName = "test";
    private int xPos = 0;
    private int yPos = 0;
    private int height = 20;
    private int width = 30;

    @Before
    public void init() throws Exception {
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getInputManager()).thenReturn(inputManager);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(loggerFactory.createLogger(Button.class)).thenReturn(logger);
        when(sprite.getImage()).thenReturn(image);
        when(image.getWidth(anyObject())).thenReturn(width);
        when(image.getHeight(anyObject())).thenReturn(height);

        button = new Button(serviceLocator, xPos, yPos, sprite, action, buttonName);
        button2 = new Button(serviceLocator, xPos, yPos, sprite, action, buttonName, dimensions);
    }

    @Test
    public void testRenderer() {
        button.render();
        verify(renderer, times(1)).drawSpriteHUD(sprite, new Point(xPos, yPos), width, height);
    }

    @Test
    public void testRegister() {
        button.register();
        verify(inputManager, times(1)).addObserver(button);
    }

    @Test
    public void testDeregister() {
        button.deregister();
        verify(inputManager, times(1)).removeObserver(button);
    }

    @Test
    public void testMouseClickedOnButton() {
        int xPosClicked = 15;
        int yPosClicked = 15;
        button.mouseClicked(xPosClicked, yPosClicked);
        verify(game, times(1)).schedule(action);
    }

    @Test
    public void testMouseClickedNotOnButton() {
        int xPosClicked = 40;
        int yPosClicked = 15;
        button.mouseClicked(xPosClicked, yPosClicked);
        verify(game, times(0)).schedule(action);
    }

    @Test
    public void testConstructor1() {
        int width = sprite.getImage().getWidth(null);
        int height = sprite.getImage().getHeight(null);
        int[] topLeft = Whitebox.getInternalState(button, "topLeft");
        int[] bottomRight = Whitebox.getInternalState(button, "bottomRight");

        assertEquals(serviceLocator, Whitebox.getInternalState(button, "serviceLocator"));
        assertEquals(logger, Whitebox.getInternalState(button, "logger"));
        assertEquals(sprite, Whitebox.getInternalState(button, "sprite"));
        assertEquals(action, Whitebox.getInternalState(button, "action"));
        assertEquals(buttonName, Whitebox.getInternalState(button, "name"));
        assertEquals(width, (int) Whitebox.getInternalState(button, "width"));
        assertEquals(height, (int) Whitebox.getInternalState(button, "height"));
        assertEquals(topLeft[0], xPos);
        assertEquals(topLeft[1], yPos);
        assertEquals(bottomRight[0], xPos + width);
        assertEquals(bottomRight[1], yPos + height);
    }

    @Test
    public void testConstructor2() {
        int width = sprite.getImage().getWidth(null);
        int height = sprite.getImage().getHeight(null);
        int[] topLeft = Whitebox.getInternalState(button2, "topLeft");
        int[] bottomRight = Whitebox.getInternalState(button2, "bottomRight");

        assertEquals(serviceLocator, Whitebox.getInternalState(button2, "serviceLocator"));
        assertEquals(logger, Whitebox.getInternalState(button2, "logger"));
        assertEquals(sprite, Whitebox.getInternalState(button2, "sprite"));
        assertEquals(action, Whitebox.getInternalState(button2, "action"));
        assertEquals(buttonName, Whitebox.getInternalState(button2, "name"));
        assertEquals(width, (int) Whitebox.getInternalState(button2, "width"));
        assertEquals(height, (int) Whitebox.getInternalState(button2, "height"));
        assertEquals(topLeft[0], xPos);
        assertEquals(topLeft[1], yPos);
        assertEquals(bottomRight[0], xPos + width);
        assertEquals(bottomRight[1], yPos + height);
    }

    @Test
    public void testConstructorNullInput1() {
        Runnable nullAction = null;
        new Button(serviceLocator, xPos, yPos, sprite, nullAction, buttonName);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullInput2() {
        Tuple2<Integer, Integer> nullDimensions = null;
        Runnable nullAction = null;
        new Button(serviceLocator, xPos, yPos, sprite, nullAction, buttonName, nullDimensions);
    }

}
