package scenes;

import logging.ILogger;
import logging.ILoggerFactory;
import rendering.IRenderer;
import system.IServiceLocator;

import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Test suite for the PauseScreen class.
 */
public class PauseScreenTest {

    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);


    private IScene pausescreen;

}
