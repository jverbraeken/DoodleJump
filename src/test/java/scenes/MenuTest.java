package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import progression.IProgressionManager;
import progression.Ranks;
import rendering.*;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;
import java.awt.Color;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class MenuTest {

    private IRenderer renderer;
    private IConstants constants;
    private IServiceLocator serviceLocator;
    private ILogger logger;
    private ILoggerFactory loggerFactory;
    private ISpriteFactory spriteFactory;
    private ISprite startCover;
    private IProgressionManager progressionManager;
    private IButtonFactory buttonFactory;
    private IButton play, scores, multiplayer, shop, chooseMode;
    private IDoodleFactory doodleFactory;
    private IDoodle doodle;
    private IPlatformFactory platformFactory;
    private IPlatform platform;
    private Menu menu;

    @Before
    public void setUp() throws Exception {

        play = mock(IButton.class);
        scores = mock(IButton.class);
        multiplayer = mock(IButton.class);
        shop = mock(IButton.class);
        chooseMode = mock(IButton.class);

        buttonFactory = mock(IButtonFactory.class);
        when(buttonFactory.createPlayButton(anyDouble(), anyDouble())).thenReturn(play);
        when(buttonFactory.createScoreButton(anyDouble(), anyDouble())).thenReturn(scores);
        when(buttonFactory.createShopButton(anyDouble(), anyDouble())).thenReturn(shop);
        when(buttonFactory.createMultiplayerButton(anyDouble(), anyDouble())).thenReturn(multiplayer);
        when(buttonFactory.createChooseModeButton(anyDouble(), anyDouble())).thenReturn(chooseMode);

        renderer = mock(IRenderer.class);

        platform = mock(IPlatform.class);

        platformFactory = mock(IPlatformFactory.class);
        when(platformFactory.createPlatform(anyInt(), anyInt())).thenReturn(platform);

        doodle = mock(IDoodle.class);
        when(doodle.checkCollision(platform)).thenReturn(true);

        doodleFactory = mock(IDoodleFactory.class);
        when(doodleFactory.createStartScreenDoodle()).thenReturn(doodle);

        constants = mock(IConstants.class);
        when(constants.getGameHeight()).thenReturn(100);
        when(constants.getGameWidth()).thenReturn(100);

        logger = mock(ILogger.class);
        loggerFactory = mock(ILoggerFactory.class);
        when(loggerFactory.createLogger(any())).thenReturn(logger);

        startCover = mock(ISprite.class);

        spriteFactory = mock(ISpriteFactory.class);
        when(spriteFactory.getSprite(any())).thenReturn(startCover);

        progressionManager = mock(IProgressionManager.class);
        when(progressionManager.getRank()).thenReturn(Ranks.theBoss);

        serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        when(serviceLocator.getButtonFactory()).thenReturn(buttonFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getDoodleFactory()).thenReturn(doodleFactory);
        when(serviceLocator.getPlatformFactory()).thenReturn(platformFactory);

        menu = Whitebox.invokeConstructor(Menu.class, serviceLocator);
    }

    @Test
    public void testRender() {
        menu.render();
        verify(renderer, times(1)).drawSpriteHUD(startCover, new Point(0, 0));

        verify(play, times(1)).render();
        verify(scores, times(1)).render();
        verify(shop, times(1)).render();
        verify(multiplayer, times(1)).render();
        verify(chooseMode, times(1)).render();

        verify(progressionManager, times(1)).getRank();

        verify(renderer).fillRectangle(new Point(0, 0), constants.getGameWidth(), 65, rendering.Color.halfOpaqueWhite);
        verify(renderer).drawText(new Point(0, 50), "Rank: " + Ranks.theBoss.getName(), rendering.Color.black);

    }

    @Test
    public void testUpdate() {
        double random = Math.random();
        menu.update(random);
        verify(doodle, times(1)).update(random);
        verify(doodle, times(1)).checkCollision(platform);
        verify(platform, times(1)).collidesWith(doodle);
    }

    @Test
    public void testStart() {
        menu.start();

        verify(play, times(1)).register();
        verify(scores, times(1)).register();
        verify(shop, times(1)).register();
        verify(multiplayer, times(1)).register();
        verify(chooseMode, times(1)).register();

        verify(logger, atLeastOnce()).info(anyString());
    }

    @Test
    public void testStop() {
        menu.stop();

        verify(play, times(1)).deregister();
        verify(scores, times(1)).deregister();
        verify(multiplayer, times(1)).deregister();
        verify(shop, times(1)).deregister();
        verify(chooseMode, times(1)).deregister();

        verify(logger, atLeastOnce()).info(anyString());
    }

}
