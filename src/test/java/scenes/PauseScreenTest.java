package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import progression.IProgressionManager;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import static org.mockito.Matchers.anyInt;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test suite for the PauseScreen class.
 */
public class PauseScreenTest {

    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IButtonFactory buttonFactory = mock(IButtonFactory.class);
    private IButton button = mock(IButton.class);
    private IButton resumeButton = mock(IButton.class);
    private IButton missionButton = mock(IButton.class);
    private IButton shopButton = mock(IButton.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private IProgressionManager progressionManager = mock(IProgressionManager.class);
    private IConstants constants = mock(IConstants.class);
    private Runnable action = mock(Runnable.class);
    ISprite[] sprites = {sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite};
    private int xPos = 0, yPos = 0;

    private IScene pausescreen;

    @Before
    public void init() throws Exception {
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(loggerFactory.createLogger(PauseScreen.class)).thenReturn(logger);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getButtonFactory()).thenReturn(buttonFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(buttonFactory.createResumeButton(anyInt(), anyInt())).thenReturn(resumeButton);
        when(buttonFactory.createSwitchToShopButton(anyInt(), anyInt())).thenReturn(shopButton);
        when(buttonFactory.createSwitchToMissionButton(anyInt(), anyInt())).thenReturn(missionButton);
        when(spriteFactory.getCoinSprite(anyInt())).thenReturn(sprite);
        when(spriteFactory.getResumeButtonSprite()).thenReturn(sprite);

        pausescreen = new PauseScreen(serviceLocator);
    }

    @Test
    public void testConstructor() throws Exception {
        assertEquals(serviceLocator, Whitebox.getInternalState(pausescreen, "serviceLocator"));
        assertEquals(logger, Whitebox.getInternalState(pausescreen, "logger"));
        assertEquals(resumeButton, Whitebox.getInternalState(pausescreen, "resumeButton"));
        assertEquals(missionButton, Whitebox.getInternalState(pausescreen, "switchMissionButton"));
        assertEquals(shopButton, Whitebox.getInternalState(pausescreen, "switchShopButton"));
        assertEquals(sprites, Whitebox.getInternalState(pausescreen, "coinSprites") );
    }

    @Test
    public void testStart() throws Exception {
        //pausescreen.start();
        //assertThat(Whitebox.getInternalState(pausescreen, "mode"), is(PauseScreenModes.mission));
    }

}
