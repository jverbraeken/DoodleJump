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
import rendering.Color;
import rendering.IRenderer;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.Point;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test suite for the ShopScreen class.
 */
public class ShopScreenTest {

    private IButton button = mock(IButton.class);
    private IButton jetpackButton, propellerButton, sizeDownButton, sizeUpButton, springButton, springShoesButton, trampolineButton;
    private IButtonFactory buttonFactory = mock(IButtonFactory.class);
    private IConstants constants = mock(IConstants.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IProgressionManager progressionManager = mock(IProgressionManager.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IScene shopScreen;
    private ISceneFactory sceneFactory = mock(ISceneFactory.class);
    private ISprite sprite = mock(ISprite.class), background = mock(ISprite.class), bottomScreen = mock(ISprite.class);
    private ISprite[] sprites = {sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite, sprite};
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    @Before
    public void init() throws Exception {
        jetpackButton = mock(IButton.class);
        propellerButton = mock(IButton.class);
        sizeDownButton = mock(IButton.class);
        sizeUpButton = mock(IButton.class);
        springButton = mock(IButton.class);
        springShoesButton = mock(IButton.class);
        trampolineButton = mock(IButton.class);

        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getSceneFactory()).thenReturn(sceneFactory);
        when(serviceLocator.getButtonFactory()).thenReturn(buttonFactory);
        when(loggerFactory.createLogger(ShopScreen.class)).thenReturn(logger);
        when(spriteFactory.getSprite(IRes.Sprites.menu)).thenReturn(sprite);
        when(spriteFactory.getSprite(IRes.Sprites.background)).thenReturn(background);
        when(spriteFactory.getSprite(IRes.Sprites.killScreenBottom)).thenReturn(bottomScreen);
        when(spriteFactory.getCoinSprite(anyInt())).thenReturn(sprite);
        when(spriteFactory.getPowerupSprite(any(Powerups.class), anyInt())).thenReturn(sprite);
        when(buttonFactory.createMainMenuButton(anyDouble(), anyDouble())).thenReturn(button);
        when(buttonFactory.createShopPowerupButton(eq(Powerups.jetpack), anyDouble(), anyDouble(), anyInt())).thenReturn(jetpackButton);
        when(buttonFactory.createShopPowerupButton(eq(Powerups.propeller), anyDouble(), anyDouble(), anyInt())).thenReturn(propellerButton);
        when(buttonFactory.createShopPowerupButton(eq(Powerups.sizeDown), anyDouble(), anyDouble(), anyInt())).thenReturn(sizeDownButton);
        when(buttonFactory.createShopPowerupButton(eq(Powerups.sizeUp), anyDouble(), anyDouble(), anyInt())).thenReturn(sizeUpButton);
        when(buttonFactory.createShopPowerupButton(eq(Powerups.spring), anyDouble(), anyDouble(), anyInt())).thenReturn(springButton);
        when(buttonFactory.createShopPowerupButton(eq(Powerups.springShoes), anyDouble(), anyDouble(), anyInt())).thenReturn(springShoesButton);
        when(buttonFactory.createShopPowerupButton(eq(Powerups.trampoline), anyDouble(), anyDouble(), anyInt())).thenReturn(trampolineButton);

        shopScreen = new ShopScreen(serviceLocator);
    }

    @Test
    public void testConstructor() throws Exception {
        assertEquals(serviceLocator, Whitebox.getInternalState(shopScreen, "serviceLocator"));
        assertEquals(logger, Whitebox.getInternalState(shopScreen, "logger"));
        assertEquals(bottomScreen, Whitebox.getInternalState(shopScreen, "bottomChooseModeScreen"));
        assertEquals(background, Whitebox.getInternalState(shopScreen, "background"));
        assertEquals(sprites, Whitebox.getInternalState(shopScreen, "coinSprites"));
    }

    @Test
    public void testContructorButtons() throws Exception {
        ArrayList<IButton> buttons = Whitebox.getInternalState(shopScreen, "buttons");
        assertEquals(button, buttons.get(0));
        assertEquals(jetpackButton, buttons.get(1));
        assertEquals(propellerButton, buttons.get(2));
        assertEquals(sizeDownButton, buttons.get(3));
        assertEquals(sizeUpButton, buttons.get(4));
        assertEquals(springButton, buttons.get(5));
        assertEquals(springShoesButton, buttons.get(6));
        assertEquals(trampolineButton, buttons.get(7));
    }

    @Test
    public void testStart() {
        shopScreen.start();
        verify(button, times(1)).register();
        verify(jetpackButton, times(1)).register();
        verify(propellerButton, times(1)).register();
        verify(sizeDownButton, times(1)).register();
        verify(sizeUpButton, times(1)).register();
        verify(springButton, times(1)).register();
        verify(springShoesButton, times(1)).register();
        verify(trampolineButton, times(1)).register();

        verify(logger, atLeastOnce()).info(anyString());
    }

    @Test
    public void testStop() {
        shopScreen.stop();
        verify(button, times(1)).deregister();
        verify(jetpackButton, times(1)).deregister();
        verify(propellerButton, times(1)).deregister();
        verify(sizeDownButton, times(1)).deregister();
        verify(sizeUpButton, times(1)).deregister();
        verify(springButton, times(1)).deregister();
        verify(springShoesButton, times(1)).deregister();
        verify(trampolineButton, times(1)).deregister();

        verify(logger, atLeastOnce()).info(anyString());
    }

    @Test
    public void testRender() throws Exception {
        shopScreen.render();
        verify(renderer, times(1)).drawSpriteHUD(background, new Point(0, 0));
        verify(renderer, times(1)).drawSpriteHUD(bottomScreen, new Point(0, 0));
        verify(renderer, atLeastOnce()).drawSpriteHUD(sprite, new Point(anyInt(), anyInt()));
        verify(renderer, atLeastOnce()).drawTextHUD(new Point(anyInt(), anyInt()), anyString(), Color.black);
        verify(button, times(1)).render();
    }

    @Test
    public void testUpdate() {
        double delta = 5d;
        double deviation = 0.001;
        double spriteIndex = 1.5d;
        shopScreen.update(delta);
        assertEquals(spriteIndex, Whitebox.getInternalState(shopScreen, "coinSpriteIndex"), deviation);
    }

    @Test
    public void testDrawTextHUD() throws Exception {
        Whitebox.invokeMethod(shopScreen, "drawPowerupText", Powerups.jetpack, 0.0, 0.0);
        verify(renderer, times(1)).drawTextHUD(any(Point.class), anyString(), eq(Color.black));
    }

    @Test(expected = NullPointerException.class)
    public void testDrawTextHUDNullInput() throws Exception {
        Powerups powerup = null;
        Whitebox.invokeMethod(shopScreen, "drawPowerupText", powerup, 0.0d, 0.0d);
    }

}
