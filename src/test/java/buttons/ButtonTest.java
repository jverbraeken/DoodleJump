package buttons;

import input.InputManager;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.IServiceLocator;
import system.Game;
import java.awt.Image;
import java.awt.Point;

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

    private IButton button;
    private static IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private Runnable action = mock(Runnable.class);
    private static ILogger logger = mock(ILogger.class);
    private static ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private ISprite sprite = mock(ISprite.class);
    private IRenderer renderer = mock(IRenderer.class);
    private Image image = mock(Image.class);
    private static InputManager inputManager = mock(InputManager.class);
    private String buttonName = "test";
    private int xPos = 0;
    private int yPos = 0;

    Game game = mock(Game.class);



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
    

}
