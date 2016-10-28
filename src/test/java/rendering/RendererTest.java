package rendering;

import constants.IConstants;
import filesystem.IFileSystem;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


public class RendererTest {

    Font font = mock(Font.class);
    Font font50 = mock(Font.class);
    Graphics2D graphics = mock(Graphics2D.class);
    ICamera camera = mock(ICamera.class);
    IConstants constants = mock(IConstants.class);
    IFileSystem fileSystem = mock(IFileSystem.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    Image image = mock(Image.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISprite sprite = mock(ISprite.class);

    Renderer renderer;

    int gameHeight = 10, gameWidth = 10;

    @Before
    public void init() throws Exception {
        when(constants.getGameHeight()).thenReturn(gameHeight);
        when(constants.getGameWidth()).thenReturn(gameWidth);
        when(fileSystem.getFont(anyString())).thenReturn(font);
        when(font.deriveFont(anyInt(), anyFloat())).thenReturn(font50);
        when(loggerFactory.createLogger(Renderer.class)).thenReturn(logger);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getFileSystem()).thenReturn(fileSystem);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(sprite.getImage()).thenReturn(image);

        Whitebox.setInternalState(Renderer.class, "serviceLocator", serviceLocator);
        renderer = Whitebox.invokeConstructor(Renderer.class);
        Whitebox.setInternalState(renderer, "graphics", graphics);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetGraphicsBufferNull() {
        renderer.setGraphicsBuffer(null);
    }

    @Test
    public void testSetGraphicsBuffer() {
        renderer.setGraphicsBuffer(graphics);
        verify(graphics, times(2)).setRenderingHint(anyObject(), anyObject());
    }

    @Test
    public void testSetCamera() {
        renderer.setCamera(camera);
        ICamera actual = Whitebox.getInternalState(renderer, "camera");
        assertThat(actual, is(camera));
    }

    @Test
    public void testGetCamera() {
        Whitebox.setInternalState(renderer, "camera", camera);
        ICamera actual = renderer.getCamera();
        assertThat(actual, is(camera));
    }

    @Test
    public void testClear() {
        renderer.clear();
        verify(graphics, times(1)).clearRect(0, 0, gameWidth, gameHeight);
    }

    @Test
    public void testDrawRectangle() {
        renderer.drawRectangle(1, 1, 10, 10);
        verify(graphics, times(1)).drawRect(1, 1, 10, 10);
    }

    @Test
    public void testDrawSprite() {
        renderer.drawSprite(sprite, 1, 1);
        verify(graphics, times(1)).drawImage(image, 1, 1, null);
    }

    @Test
    public void testDrawSpriteRotate() {
        renderer.drawSprite(sprite, 1, 1, 90);
        verify(graphics, times(1)).rotate(90);
        verify(graphics, times(1)).drawImage(image, 0, 0, null);
        verify(graphics, times(1)).rotate(-90);
    }

    @Test
    public void testDrawSpriteWidthHeight() {
        renderer.drawSprite(sprite, 1, 1, 10, 10);
        verify(graphics, times(1)).drawImage(image, 1, 1, 10, 10, null);
    }

    @Test
    public void drawSpriteWidthHeightRotate() {
        renderer.drawSprite(sprite, 1, 1, 10, 10, 90);
        verify(graphics, times(1)).rotate(90);
        verify(graphics, times(1)).drawImage(image, 0, 0, 10, 10, null);
        verify(graphics, times(1)).rotate(-90);
    }

}
