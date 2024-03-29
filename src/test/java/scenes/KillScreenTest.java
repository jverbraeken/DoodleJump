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
import progression.Ranks;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class KillScreenTest {

    private IRenderer renderer;
    private IConstants constants;
    private IServiceLocator serviceLocator;
    private ILogger logger;
    private ILoggerFactory loggerFactory;
    private ISpriteFactory spriteFactory;
    private ISprite background, bottomKillSCreen, gameOver;
    private IProgressionManager progressionManager;
    private IButtonFactory buttonFactory;
    private IButton playagain, menu;
    private KillScreen killScreen;

    @Before
    public void setUp() throws Exception {

        playagain = mock(IButton.class);
        menu = mock(IButton.class);

        buttonFactory = mock(IButtonFactory.class);
        when(buttonFactory.createPlayAgainButton(anyDouble(), anyDouble())).thenReturn(playagain);
        when(buttonFactory.createMainMenuButton(anyDouble(), anyDouble())).thenReturn(menu);

        renderer = mock(IRenderer.class);

        constants = mock(IConstants.class);
        when(constants.getGameHeight()).thenReturn(100);
        when(constants.getGameWidth()).thenReturn(100);

        logger = mock(ILogger.class);
        loggerFactory = mock(ILoggerFactory.class);
        when(loggerFactory.createLogger(any())).thenReturn(logger);

        background = mock(ISprite.class);
        bottomKillSCreen = mock(ISprite.class);
        when(bottomKillSCreen.getHeight()).thenReturn(10);
        gameOver = mock(ISprite.class);

        spriteFactory = mock(ISpriteFactory.class);
        when(spriteFactory.getSprite(any())).thenReturn(background);

        progressionManager = mock(IProgressionManager.class);
        when(progressionManager.getRank()).thenReturn(Ranks.theBoss);

        serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        when(serviceLocator.getButtonFactory()).thenReturn(buttonFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getConstants()).thenReturn(constants);

        killScreen = Whitebox.invokeConstructor(KillScreen.class, serviceLocator, 500, 499);
    }

    @Test
    public void testRender() {
        killScreen.render();
        verify(renderer, times(1)).drawSpriteHUD(background, new Point(0, 0));
        verify(renderer, times(1)).drawSpriteHUD(background, new Point(10, 30));

        verify(menu, times(1)).render();
        verify(playagain, times(1)).render();

        verify(progressionManager, times(1)).getRank();
    }

    @Test
    public void testUpdate() {
        Whitebox.setInternalState(killScreen, "expCount", 0);
        Whitebox.setInternalState(killScreen, "totalExperience", 100);
        Whitebox.setInternalState(killScreen, "countUpAmount", 1000);
        killScreen.update(1000);
        assertEquals(1000, (int) Whitebox.getInternalState(killScreen, "expCount"));
    }

    @Test
    public void testStart() {
        killScreen.start();

        verify(playagain, times(1)).register();
        verify(menu, times(1)).register();

        verify(logger, atLeastOnce()).info(anyString());
    }

    @Test
    public void testStop() {
        killScreen.stop();

        verify(playagain, times(1)).deregister();
        verify(menu, times(1)).deregister();
        
        verify(logger, atLeastOnce()).info(anyString());
    }

}
