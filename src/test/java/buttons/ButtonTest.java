package buttons;

import groovy.lang.Tuple2;
import input.InputManager;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
 * Created by Michael on 11/1/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({InputManager.class, Game.class})
public class ButtonTest {

    private static IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private static ILogger logger = mock(ILogger.class);
    private static ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private static InputManager inputManager = mock(InputManager.class);
    Game game = mock(Game.class);
    private IButton button, button2, nullButton;
    private Runnable action = mock(Runnable.class);
    private ISprite sprite = mock(ISprite.class);
    private IRenderer renderer = mock(IRenderer.class);
    private Image image = mock(Image.class);
    private String buttonName = "test";
    private int xPos = 0;
    private int yPos = 0;
    final Tuple2<Integer, Integer> dimensions = new Tuple2<>(30, 20);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() throws Exception {
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getInputManager()).thenReturn(inputManager);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(loggerFactory.createLogger(Button.class)).thenReturn(logger);
        when(sprite.getImage()).thenReturn(image);
        when(image.getWidth(anyObject())).thenReturn(30);
        when(image.getHeight(anyObject())).thenReturn(20);

        button = new Button(serviceLocator, xPos, yPos, sprite, action, buttonName);
        button2 = new Button(serviceLocator, xPos, yPos, sprite, action, buttonName, dimensions);
    }

    @Test
    public void testRenderer() {
        button.render();
        verify(renderer, times(1)).drawSpriteHUD(sprite, new Point(xPos, yPos), 30, 20);
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

    }

    @Test (expected=AssertionError.class)
    public void testConstructorNullInput1() {
        ISprite nullSprite = null;
        Runnable nullAction = null;
        IServiceLocator nullServiceLocator = null;

        nullButton = new Button(nullServiceLocator, xPos, yPos, nullSprite, nullAction, buttonName);
    }

    @Test
    public void testConstructorNullInput2() {
        Runnable nullAction = null;
        nullButton = new Button(serviceLocator, xPos, yPos, sprite, nullAction, buttonName);
    }

}
