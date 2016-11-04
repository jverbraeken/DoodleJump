package scenes;

import buttons.IButton;
import buttons.IButtonFactory;
import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.powerups.Powerups;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import progression.IProgressionManager;
import progression.Mission;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test suite for the PauseScreen class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Mission.class})
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
    private IButton powerupButton = mock(IButton.class);
    private ISprite sprite = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private IProgressionManager progressionManager = mock(IProgressionManager.class);
    private IConstants constants = mock(IConstants.class);
    private Runnable action = mock(Runnable.class);
    ISprite[] sprites = {sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite};
    private Mission mission = mock(Mission.class);
    int spriteHeight = 100;
    int yPos = 50;
    EnumMap<Powerups, IButton> buttonMap = new EnumMap<>(Powerups.class);

    private List<Mission> missions = new ArrayList<>();


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
        when(buttonFactory.createPausePowerupButton(anyObject(), anyDouble(), anyDouble())).thenReturn(powerupButton);
        when(spriteFactory.getCoinSprite(anyInt())).thenReturn(sprite);
        when(spriteFactory.getResumeButtonSprite()).thenReturn(sprite);
        when(spriteFactory.getAchievementSprite()).thenReturn(sprite);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        when(sprite.getHeight()).thenReturn(spriteHeight);

        pausescreen = new PauseScreen(serviceLocator);
    }

    @Test
    public void testConstructor1() throws Exception {
        assertEquals(serviceLocator, Whitebox.getInternalState(pausescreen, "serviceLocator"));
        assertEquals(logger, Whitebox.getInternalState(pausescreen, "logger"));
        assertEquals(resumeButton, Whitebox.getInternalState(pausescreen, "resumeButton"));
        assertEquals(missionButton, Whitebox.getInternalState(pausescreen, "switchMissionButton"));
        assertEquals(shopButton, Whitebox.getInternalState(pausescreen, "switchShopButton"));
        assertEquals(sprites, Whitebox.getInternalState(pausescreen, "coinSprites") );
    }

    @Test (expected = NullPointerException.class)
    public void testConstructor2() throws Exception {
        IServiceLocator nullServiceLocator = null;
        new PauseScreen(nullServiceLocator);
    }

    @Test
    public void testStart() throws Exception {
        pausescreen.start();
        assertThat(Whitebox.getInternalState(pausescreen, "mode"), is(PauseScreenModes.mission));
        verify(resumeButton, times(1)).register();
        verify(shopButton, times(1)).register();
    }

    @Test
    public void testStop1() throws Exception {
        pausescreen.stop();
        verify(resumeButton, times(1)).deregister();
        verify(shopButton, times(1)).deregister();
    }

    @Test
    public void testStop2() throws Exception {
        Whitebox.setInternalState(pausescreen, "mode", PauseScreenModes.shop);
        pausescreen.stop();
        verify(resumeButton, times(1)).deregister();
    }

    @Test
    public void testRenderMssions() throws Exception{
        missions.add(mission);
        when(progressionManager.getMissions()).thenReturn(missions);
        Whitebox.invokeMethod(pausescreen, "renderMissions", yPos);
        verify(shopButton, times(1)).render();
        verify(mission, times(1)).render(anyInt());
    }


    @Test
    public void testRenderShop() throws Exception {
        buttonMap.put(Powerups.jetpack, button);
        Whitebox.setInternalState(pausescreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pausescreen, "renderShop");
        verify(button, times(1)).render();
    }

    @Test
    public void testUpdate() throws Exception {
        double newSpriteIndex = 5.0;
        double delta = 150d;
        double deviation = 0.001;
        Whitebox.invokeMethod(pausescreen, "update", delta);
        assertEquals(newSpriteIndex, (double) Whitebox.getInternalState(pausescreen, "coinSpriteIndex"), deviation);
    }

    @Test
    public void testUpdateButton1() throws Exception {
        double newXPos = 50d;
        double newYPos = 60d;
        buttonMap.put(Powerups.jetpack, button);
        Whitebox.setInternalState(pausescreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pausescreen, "updateButton", Powerups.jetpack, newXPos, newYPos);
        verify(button, times(1)).deregister();
        verify(powerupButton, times(1)).register();
    }

    @Test
    public void testUpdateButton2() throws Exception {
        double newXPos = 50d;
        double newYPos = 60d;
        buttonMap.put(Powerups.jetpack, button);
        Whitebox.setInternalState(pausescreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pausescreen, "updateButton", Powerups.jetpack, newXPos, newYPos);
        EnumMap<Powerups, IButton> buttonMap2 = Whitebox.getInternalState(pausescreen, "buttonMap");
        assertNotEquals(button, buttonMap2.get(Powerups.jetpack));
    }

    @Test
    public void testCreatePowerupButton() throws Exception {
        Whitebox.invokeMethod(pausescreen, "createPowerupButton");
        EnumMap<Powerups, IButton> buttonMap3 = Whitebox.getInternalState(pausescreen, "buttonMap");
        assertEquals(powerupButton, buttonMap3.get(Powerups.jetpack));
        assertEquals(powerupButton, buttonMap3.get(Powerups.propeller));
        assertEquals(powerupButton, buttonMap3.get(Powerups.sizeDown));
        assertEquals(powerupButton, buttonMap3.get(Powerups.sizeUp));
        assertEquals(powerupButton, buttonMap3.get(Powerups.spring));
        assertEquals(powerupButton, buttonMap3.get(Powerups.springShoes));
        assertEquals(powerupButton, buttonMap3.get(Powerups.trampoline));
    }

    @Test
    public void testSetShopCover1() throws Exception {
        Whitebox.invokeMethod(pausescreen, "setShopCover");
        verify(missionButton, times(1)).register();
        verify(powerupButton, times(7)).register();
    }

    @Test
    public void testSetShopCover2() throws Exception {
        buttonMap.put(Powerups.jetpack, powerupButton);
        Whitebox.setInternalState(pausescreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pausescreen, "setShopCover");
        verify(missionButton, times(1)).register();
        verify(powerupButton, times(1)).register();
    }

    @After
    public void cleanUp() {
        missions.clear();
        buttonMap.clear();
    }
}
