package buttons;

import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Image;

import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Michael on 11/1/2016.
 */
public class ButtonTest {

    private IButton button;
    private static IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private Runnable action = mock(Runnable.class);
    private static ILogger logger = mock(ILogger.class);
    private static ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private ISprite sprite = mock(ISprite.class);
    private IRenderer renderer = mock(IRenderer.class);
    private Image image = mock(Image.class);
//    private static InputManager inputManager = mock(InputManager.class);
    private String buttonName = "test";
    private int xPos = 0;
    private int yPos = 0;



    @Before
    public void init() throws Exception {
        when(serviceLocator.getRenderer()).thenReturn(renderer);
  //      when(serviceLocator.getInputManager()).thenReturn(inputManager);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(sprite.getImage()).thenReturn(image);
        when(image.getWidth(anyObject())).thenReturn(30);
        when(image.getHeight(anyObject())).thenReturn(20);

        button = new Button(serviceLocator, xPos, yPos, sprite, action, buttonName);
    }

    @Test
    public void testRenderer() {

    }
}
