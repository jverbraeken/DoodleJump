package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.powerups.Powerups;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import progression.IProgressionManager;
import progression.Ranks;
import rendering.Color;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;
import java.util.EnumMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class PauseScreenTest {

    private IRenderer renderer;
    private IConstants constants;
    private IServiceLocator serviceLocator;
    private ILogger logger;
    private ILoggerFactory loggerFactory;
    private ISpriteFactory spriteFactory;
    private ISprite coinsprites, background, scoreBar, powerup;
    private IProgressionManager progressionManager;
    private IButtonFactory buttonFactory;
    private IButton resume, switchMission, switchShop, jetPack, propellor, sizeUp, sizeDown,spring, springShoes, trampoline;
    private PauseScreen pauseScreen;

    @Before
    public void setUp() throws Exception {

        resume = mock(IButton.class);
        switchMission = mock(IButton.class);
        switchShop = mock(IButton.class);
        jetPack = mock(IButton.class);
        propellor = mock(IButton.class);
        sizeDown = mock(IButton.class);
        sizeUp = mock(IButton.class);
        spring = mock(IButton.class);
        springShoes = mock(IButton.class);
        jetPack = mock(IButton.class);

        buttonFactory = mock(IButtonFactory.class);
        when(buttonFactory.createResumeButton(anyDouble(), anyDouble())).thenReturn(resume);
        when(buttonFactory.createSwitchToMissionButton(anyDouble(), anyDouble())).thenReturn(switchMission);
        when(buttonFactory.createSwitchToShopButton(anyDouble(), anyDouble())).thenReturn(switchShop);
        when(buttonFactory.createPausePowerupButton(eq(Powerups.jetpack), anyDouble(), anyDouble())).thenReturn(jetPack);
        when(buttonFactory.createPausePowerupButton(eq(Powerups.sizeUp), anyDouble(), anyDouble())).thenReturn(sizeUp);
        when(buttonFactory.createPausePowerupButton(eq(Powerups.sizeDown), anyDouble(), anyDouble())).thenReturn(sizeDown);
        when(buttonFactory.createPausePowerupButton(eq(Powerups.propeller), anyDouble(), anyDouble())).thenReturn(propellor);
        when(buttonFactory.createPausePowerupButton(eq(Powerups.spring), anyDouble(), anyDouble())).thenReturn(spring);
        when(buttonFactory.createPausePowerupButton(eq(Powerups.trampoline), anyDouble(), anyDouble())).thenReturn(trampoline);
        when(buttonFactory.createPausePowerupButton(eq(Powerups.springShoes), anyDouble(), anyDouble())).thenReturn(springShoes);

        renderer = mock(IRenderer.class);

        constants = mock(IConstants.class);
        when(constants.getGameHeight()).thenReturn(100);

        logger = mock(ILogger.class);
        loggerFactory = mock(ILoggerFactory.class);
        when(loggerFactory.createLogger(any())).thenReturn(logger);
        background = mock(ISprite.class);

        scoreBar = mock(ISprite.class);
        when(scoreBar.getHeight()).thenReturn(10);
        powerup = mock(ISprite.class);
        when(powerup.getHeight()).thenReturn(10);
        coinsprites = mock(ISprite.class);
        when(coinsprites.getHeight()).thenReturn(1);
        when(coinsprites.getWidth()).thenReturn(1);
        spriteFactory = mock(ISpriteFactory.class);
        when(spriteFactory.getPauseCoverSprite(any())).thenReturn(background);
        when(spriteFactory.getCoinSprite(anyInt())).thenReturn(coinsprites);
        when(spriteFactory.getScoreBarSprite()).thenReturn(scoreBar);
        when(spriteFactory.getPowerupSprite(any(Powerups.class), anyInt())).thenReturn(powerup);

        progressionManager = mock(IProgressionManager.class);
        when(progressionManager.getRank()).thenReturn(Ranks.theBoss);
        when(progressionManager.getCoins()).thenReturn(100);
        when(progressionManager.getPowerupLevel(any())).thenReturn(0);

        serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        when(serviceLocator.getButtonFactory()).thenReturn(buttonFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getConstants()).thenReturn(constants);

        pauseScreen = Whitebox.invokeConstructor(PauseScreen.class, serviceLocator);
    }

    @Test
    public void testRender() {
        pauseScreen.render();
        verify(renderer, times(1)).drawSpriteHUD(background, new Point(0, 0));
        verify(renderer, atLeastOnce()).drawSpriteHUD(coinsprites, new Point(anyInt(), anyInt()));

        verify(renderer, atLeastOnce()).drawTextHUD(new Point(anyInt(), anyInt()), anyString(), Color.black);


        verify(resume, times(1)).render();
    }

    @Test
    public void testStart() {
        pauseScreen.start();

        assertEquals(PauseScreenModes.mission, (PauseScreenModes) Whitebox.getInternalState(pauseScreen, "mode"));
        verify(resume, times(1)).register();
        verify(switchShop, times(1)).register();

        verify(logger, times(1)).info("The resume button is now available");
        verify(logger, times(1)).info("The switch button is now available");
    }

    @Test
    public void testStopModeShop() {
        Whitebox.setInternalState(pauseScreen, "mode", PauseScreenModes.shop);
        EnumMap<Powerups, IButton> map = Whitebox.getInternalState(pauseScreen, "buttonMap");
        map.put(Powerups.jetpack, jetPack);
        Whitebox.setInternalState(pauseScreen, "buttonMap", map);

        pauseScreen.stop();

        verify(resume, times(1)).deregister();
        verify(jetPack, times(1)).deregister();
        verify(switchShop, times(0)).deregister();

        verify(logger, times(1)).info("The powerup buttons are no longer available");
        verify(logger, times(1)).info("The resume button is no longer available");
        verify(logger, times(1)).info("The switch button to the mission cover is no longer available");
        verify(logger, times(0)).info("The switch button to the shop cover is no longer available");
    }

    @Test
    public void testStopNotModeShop() {
        Whitebox.setInternalState(pauseScreen, "mode", PauseScreenModes.mission);
        EnumMap<Powerups, IButton> map = Whitebox.getInternalState(pauseScreen, "buttonMap");
        map.put(Powerups.jetpack, jetPack);
        Whitebox.setInternalState(pauseScreen, "buttonMap", map);

        pauseScreen.stop();

        verify(resume, times(1)).deregister();
        verify(jetPack, times(0)).deregister();
        verify(switchShop, times(1)).deregister();

        verify(logger, times(0)).info("The powerup buttons are no longer available");
        verify(logger, times(1)).info("The resume button is no longer available");
        verify(logger, times(0)).info("The switch button to the mission cover is no longer available");
        verify(logger, times(1)).info("The switch button to the shop cover is no longer available");

    }

    @Test
    public void testUpdate() {
        Whitebox.setInternalState(pauseScreen, "coinSpriteIndex", 0);
        pauseScreen.update(1d);
        assertEquals(0.3d, (double) Whitebox.getInternalState(pauseScreen, "coinSpriteIndex"), 0.01);
    }


    @Test
    public void testUpdateButton() {
        Whitebox.setInternalState(pauseScreen, "mode", PauseScreenModes.mission);
        EnumMap<Powerups, IButton> map = Whitebox.getInternalState(pauseScreen, "buttonMap");
        map.put(Powerups.jetpack, jetPack);
        Whitebox.setInternalState(pauseScreen, "buttonMap", map);

        pauseScreen.updateButton(Powerups.jetpack, 99, 123);

        verify(jetPack).deregister();
        verify(jetPack).register();
    }

    @Test
    public void testCreatePowerupButton() throws Exception {
        Whitebox.invokeMethod(pauseScreen, "createPowerupbutton");
        EnumMap<Powerups, IButton> map = Whitebox.getInternalState(pauseScreen, "buttonMap");

        assertEquals(jetPack, map.get(Powerups.jetpack));
        assertEquals(propellor, map.get(Powerups.propeller));
        assertEquals(sizeDown, map.get(Powerups.sizeDown));
        assertEquals(sizeUp, map.get(Powerups.sizeUp));
        assertEquals(spring, map.get(Powerups.spring));
        assertEquals(springShoes, map.get(Powerups.springShoes));
        assertEquals(trampoline, map.get(Powerups.trampoline));
    }

    @Test
    public void testSwitchDisplay() {

    }

}
