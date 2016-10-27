package rendering;

import filesystem.IFileSystem;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
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
    IFileSystem fileSystem = mock(IFileSystem.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);

    Renderer renderer;

    @Before
    public void init() throws Exception {
        when(fileSystem.getFont(anyString())).thenReturn(font);
        when(font.deriveFont(anyInt(), anyFloat())).thenReturn(font50);
        when(loggerFactory.createLogger(Renderer.class)).thenReturn(logger);
        when(serviceLocator.getFileSystem()).thenReturn(fileSystem);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);

        Whitebox.setInternalState(Renderer.class, "serviceLocator", serviceLocator);
        renderer = Whitebox.invokeConstructor(Renderer.class);
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

}
