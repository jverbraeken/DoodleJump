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

public class ChooseModeTest {

    private IRenderer renderer;
    private IConstants constants;
    private IServiceLocator serviceLocator;
    private ILogger logger;
    private ILoggerFactory loggerFactory;
    private ISpriteFactory spriteFactory;
    private ISprite background;
    private ISprite bottomChooseModeScreen;
    private IProgressionManager progressionManager;
    private IButtonFactory buttonFactory;
    private IButton regular, space, underwater, darkness, horizontal, vertical, menu;
    private ChooseModeScreen chooseMode;

    @Before
    public void setUp() throws Exception {

        regular = mock(IButton.class);
        space = mock(IButton.class);
        underwater = mock(IButton.class);
        darkness = mock(IButton.class);
        horizontal = mock(IButton.class);
        vertical = mock(IButton.class);
        menu = mock(IButton.class);

        buttonFactory = mock(IButtonFactory.class);
        when(buttonFactory.createDarknessModeButton(anyDouble(), anyDouble())).thenReturn(darkness);
        when(buttonFactory.createInvertModeButton(anyDouble(), anyDouble())).thenReturn(vertical);
        when(buttonFactory.createRegularModeButton(anyDouble(), anyDouble())).thenReturn(regular);
        when(buttonFactory.createUnderwaterModeButton(anyDouble(), anyDouble())).thenReturn(underwater);
        when(buttonFactory.createSpaceModeButton(anyDouble(), anyDouble())).thenReturn(space);
        when(buttonFactory.createStoryModeButton(anyDouble(), anyDouble())).thenReturn(horizontal);
        when(buttonFactory.createMainMenuButton(anyDouble(), anyDouble())).thenReturn(menu);

        renderer = mock(IRenderer.class);

        constants = mock(IConstants.class);
        when(constants.getGameHeight()).thenReturn(100);

        logger = mock(ILogger.class);
        loggerFactory = mock(ILoggerFactory.class);
        when(loggerFactory.createLogger(any())).thenReturn(logger);
        background = mock(ISprite.class);

        bottomChooseModeScreen = mock(ISprite.class);
        when(bottomChooseModeScreen.getHeight()).thenReturn(10);
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

        chooseMode = Whitebox.invokeConstructor(ChooseModeScreen.class, serviceLocator);
    }

    @Test
    public void testRenderNoActivePopup() {
        chooseMode.render();
        verify(renderer, times(1)).drawSpriteHUD(background, new Point(0, 0));
        verify(renderer, times(1)).drawSpriteHUD(background, new Point(0, 100));

        verify(regular, times(1)).render();
        verify(space, times(1)).render();
        verify(darkness, times(1)).render();
        verify(underwater, times(1)).render();
        verify(horizontal, times(1)).render();
        verify(vertical, times(1)).render();

        verify(regular, times(1)).register();
        verify(space, times(1)).register();
        verify(darkness, times(1)).register();
        verify(underwater, times(1)).register();
        verify(horizontal, times(1)).register();
        verify(vertical, times(1)).register();
    }

    @Test
    public void testRenderActivePopup() {
        Whitebox.setInternalState(ChooseModeScreen.class, "activePopup", true);
        chooseMode.render();
        verify(renderer, times(1)).drawSpriteHUD(background, new Point(0, 0));
        verify(renderer, times(1)).drawSpriteHUD(background, new Point(0, 100));

        verify(regular, times(1)).render();
        verify(space, times(1)).render();
        verify(darkness, times(1)).render();
        verify(underwater, times(1)).render();
        verify(horizontal, times(1)).render();
        verify(vertical, times(1)).render();

        verify(regular, times(1)).deregister();
        verify(space, times(1)).deregister();
        verify(darkness, times(1)).deregister();
        verify(underwater, times(1)).deregister();
        verify(horizontal, times(1)).deregister();
        verify(vertical, times(1)).deregister();
    }

    @Test
    public void testStart() {
        chooseMode.start();

        verify(regular, times(1)).register();
        verify(space, times(1)).register();
        verify(darkness, times(1)).register();
        verify(underwater, times(1)).register();
        verify(horizontal, times(1)).register();
        verify(vertical, times(1)).register();

        verify(logger, atLeastOnce()).info(anyString());
    }

    @Test
    public void testStop() {
        chooseMode.stop();

        verify(regular, times(1)).deregister();
        verify(space, times(1)).deregister();
        verify(darkness, times(1)).deregister();
        verify(underwater, times(1)).deregister();
        verify(horizontal, times(1)).deregister();
        verify(vertical, times(1)).deregister();

        verify(logger, atLeastOnce()).info(anyString());
    }

}
