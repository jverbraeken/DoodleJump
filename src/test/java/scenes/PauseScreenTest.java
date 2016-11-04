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

import java.awt.Point;
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

    int spriteHeight = 100;
    int yPos = 50;
    private EnumMap<Powerups, IButton> buttonMap = new EnumMap<>(Powerups.class);
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
    private ISprite missionSprite = mock(ISprite.class);
    private ISprite shopSprite = mock(ISprite.class);
    private ISprite[] sprites = {sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite};
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private IProgressionManager progressionManager = mock(IProgressionManager.class);
    private IConstants constants = mock(IConstants.class);
    private Mission mission = mock(Mission.class);
    private List<Mission> missions = new ArrayList<>();

    private IScene pauseScreen;

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
        when(spriteFactory.getScoreBarSprite()).thenReturn(sprite);
        when(spriteFactory.getPauseCoverSprite(PauseScreenModes.mission)).thenReturn(missionSprite);
        when(spriteFactory.getPauseCoverSprite(PauseScreenModes.shop)).thenReturn(shopSprite);
        when(sprite.getHeight()).thenReturn(spriteHeight);

        pauseScreen = new PauseScreen(serviceLocator);
    }

    @Test
    public void testConstructor1() throws Exception {
        assertEquals(serviceLocator, Whitebox.getInternalState(pauseScreen, "serviceLocator"));
        assertEquals(logger, Whitebox.getInternalState(pauseScreen, "logger"));
        assertEquals(resumeButton, Whitebox.getInternalState(pauseScreen, "resumeButton"));
        assertEquals(missionButton, Whitebox.getInternalState(pauseScreen, "switchMissionButton"));
        assertEquals(shopButton, Whitebox.getInternalState(pauseScreen, "switchShopButton"));
        assertEquals(sprites, Whitebox.getInternalState(pauseScreen, "coinSprites"));
    }

    @Test(expected = NullPointerException.class)
    public void testConstructor2() throws Exception {
        IServiceLocator nullServiceLocator = null;
        new PauseScreen(nullServiceLocator);
    }

    @Test
    public void testStart() throws Exception {
        pauseScreen.start();
        assertThat(Whitebox.getInternalState(pauseScreen, "mode"), is(PauseScreenModes.mission));
        verify(resumeButton, times(1)).register();
        verify(shopButton, times(1)).register();
    }

    @Test
    public void testStop1() throws Exception {
        pauseScreen.stop();
        verify(resumeButton, times(1)).deregister();
        verify(shopButton, times(1)).deregister();
    }

    @Test
    public void testStop2() throws Exception {
        Whitebox.setInternalState(pauseScreen, "mode", PauseScreenModes.shop);
        pauseScreen.stop();
        verify(resumeButton, times(1)).deregister();
    }

    @Test
    public void testRenderMssions() throws Exception {
        missions.add(mission);
        when(progressionManager.getMissions()).thenReturn(missions);
        Whitebox.invokeMethod(pauseScreen, "renderMissions", yPos);
        verify(shopButton, times(1)).render();
        verify(mission, times(1)).render(anyInt());
    }


    @Test
    public void testRenderShop() throws Exception {
        buttonMap.put(Powerups.jetpack, button);
        Whitebox.setInternalState(pauseScreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pauseScreen, "renderShop");
        verify(button, times(1)).render();
    }

    @Test
    public void testUpdate() throws Exception {
        double newSpriteIndex = 5.0;
        double delta = 150d;
        double deviation = 0.001;
        Whitebox.invokeMethod(pauseScreen, "update", delta);
        assertEquals(newSpriteIndex, Whitebox.getInternalState(pauseScreen, "coinSpriteIndex"), deviation);
    }

    @Test
    public void testUpdateButton1() throws Exception {
        double newXPos = 50d;
        double newYPos = 60d;
        buttonMap.put(Powerups.jetpack, button);
        Whitebox.setInternalState(pauseScreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pauseScreen, "updateButton", Powerups.jetpack, newXPos, newYPos);
        verify(button, times(1)).deregister();
        verify(powerupButton, times(1)).register();
    }

    @Test
    public void testUpdateButton2() throws Exception {
        double newXPos = 50d;
        double newYPos = 60d;
        buttonMap.put(Powerups.jetpack, button);
        Whitebox.setInternalState(pauseScreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pauseScreen, "updateButton", Powerups.jetpack, newXPos, newYPos);
        EnumMap<Powerups, IButton> buttonMap2 = Whitebox.getInternalState(pauseScreen, "buttonMap");
        assertNotEquals(button, buttonMap2.get(Powerups.jetpack));
    }

    @Test
    public void testCreatePowerupButton() throws Exception {
        Whitebox.invokeMethod(pauseScreen, "createPowerupButton");
        EnumMap<Powerups, IButton> buttonMap3 = Whitebox.getInternalState(pauseScreen, "buttonMap");
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
        Whitebox.invokeMethod(pauseScreen, "setShopCover");
        verify(missionButton, times(1)).register();
        verify(powerupButton, times(7)).register();
    }

    @Test
    public void testSetShopCover2() throws Exception {
        buttonMap.put(Powerups.jetpack, powerupButton);
        Whitebox.setInternalState(pauseScreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pauseScreen, "setShopCover");
        verify(missionButton, times(1)).register();
        verify(powerupButton, times(1)).register();
    }

    @Test
    public void testStopShopCover1() throws Exception{
        Whitebox.invokeMethod(pauseScreen, "stopShopCover");
        verify(missionButton, times(1)).deregister();
    }

    @Test
    public void testStopShopCover2() throws Exception{
        buttonMap.put(Powerups.jetpack, powerupButton);
        buttonMap.put(Powerups.propeller, powerupButton);
        Whitebox.setInternalState(pauseScreen, "buttonMap", buttonMap);
        Whitebox.invokeMethod(pauseScreen, "stopShopCover");
        verify(missionButton, times(1)).deregister();
        verify(powerupButton, times(2)).deregister();
    }

    @Test
    public void testSwitchDisplay1() {
        pauseScreen.switchDisplay(PauseScreenModes.mission);
        verify(missionButton, times(1)).deregister();
        verify(shopButton, times(1)).register();
    }

    @Test
    public void testSwitchDisplay2() {
        pauseScreen.switchDisplay(PauseScreenModes.shop);
        verify(missionButton, times(1)).register();
        verify(shopButton, times(1)).deregister();
    }

    @Test (expected = NullPointerException.class)
    public void testSwitchDisplay3() {
        pauseScreen.switchDisplay(null);
    }

    @Test
    public void testRender1() {
        Whitebox.setInternalState(pauseScreen, "mode", PauseScreenModes.mission);
        pauseScreen.render();
        verify(renderer, times(1)).drawSpriteHUD(missionSprite, new Point(0, 0));
        verify(resumeButton, times(1)).render();
        verify(shopButton, times(1)).render();
    }

    @Test
    public void testRender2() {
        Whitebox.setInternalState(pauseScreen, "mode", PauseScreenModes.shop);
        pauseScreen.render();
        verify(renderer, times(1)).drawSpriteHUD(shopSprite, new Point(0, 0));
        verify(resumeButton, times(1)).render();
        verify(missionButton, times(1)).render();
    }

    @After
    public void cleanUp() {
        missions.clear();
        buttonMap.clear();
    }
}
