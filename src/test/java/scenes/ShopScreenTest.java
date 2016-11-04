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
import rendering.IRenderer;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by Michael on 11/4/2016.
 */
public class ShopScreenTest {

    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IButton jetpackButton, propellerButton, sizeDownButton, sizeUpButton, springButton, springShoesButton, trampolineButton;
    private IButtonFactory buttonFactory = mock(IButtonFactory.class);
    private ISprite sprite = mock(ISprite.class), background = mock(ISprite.class), bottomScreen = mock(ISprite.class);
    private ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private IRenderer renderer = mock(IRenderer.class);
    private IConstants constants = mock(IConstants.class);
    private IProgressionManager progressionManager = mock(IProgressionManager.class);

    private IScene shopScreen;

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
        when(loggerFactory.createLogger(ShopScreen.class)).thenReturn(logger);
        when(spriteFactory.getSprite(IRes.Sprites.background)).thenReturn(background);
        when(spriteFactory.getSprite(IRes.Sprites.killScreenBottom)).thenReturn(bottomScreen);
        when(spriteFactory.getCoinSprite(anyInt())).thenReturn(sprite);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        when(buttonFactory.createShopPowerupButton(Powerups.jetpack, anyDouble(), anyDouble(), anyInt())).thenReturn(jetpackButton);
        when(buttonFactory.createShopPowerupButton(Powerups.propeller, anyDouble(), anyDouble(), anyInt())).thenReturn(propellerButton);
        when(buttonFactory.createShopPowerupButton(Powerups.sizeDown, anyDouble(), anyDouble(), anyInt())).thenReturn(sizeDownButton);
        when(buttonFactory.createShopPowerupButton(Powerups.sizeUp, anyDouble(), anyDouble(), anyInt())).thenReturn(sizeUpButton);
        when(buttonFactory.createShopPowerupButton(Powerups.spring, anyDouble(), anyDouble(), anyInt())).thenReturn(springButton);
        when(buttonFactory.createShopPowerupButton(Powerups.springShoes, anyDouble(), anyDouble(), anyInt())).thenReturn(springShoesButton);
        when(buttonFactory.createShopPowerupButton(Powerups.trampoline, anyDouble(), anyDouble(), anyInt())).thenReturn(trampolineButton);

        shopScreen = new ShopScreen(serviceLocator);
    }

    @Test
    public void testConstructor() throws Exception {
        assertEquals(serviceLocator, Whitebox.getInternalState(shopScreen, "serviceLocator"));
    }

}
