package objects.blocks.platform;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import math.ICalc;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import resources.IRes;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

import java.awt.*;
import java.lang.annotation.ElementType;

import static objects.blocks.ElementTypes.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.doubleThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PrepareForTest(PlatformFactory.class)
public class PlatformFactoryTest {

    private double randomX;
    private double randomY;
    private double random;
    private ISprite sprite;
    private IServiceLocator serviceLocator;
    private ILoggerFactory loggerFactory;
    private ISpriteFactory spriteFactory;
    private IConstants constants;
    private ICalc calc;
    private ILogger logger;
    private PlatformFactory platformFactory;

    @Before
    public void setUp() throws Exception {
        randomX = Math.random()*1000;
        randomY = Math.random()*1000;
        random = Math.random();

        sprite = mock(ISprite.class);
        logger = mock(ILogger.class);
        calc = mock(ICalc.class);
        when(calc.getRandomDouble(anyDouble())).thenReturn(randomX);
        constants = mock(IConstants.class);
        when(constants.getGameHeight()).thenReturn(100);
        spriteFactory = mock(ISpriteFactory.class);
        when(spriteFactory.getSprite(Matchers.<IRes.Sprites>any())).thenReturn(sprite);
        loggerFactory = mock(ILoggerFactory.class);
        when(loggerFactory.createLogger(any())).thenReturn(logger);
        serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getCalc()).thenReturn(calc);
        when(serviceLocator.getConstants()).thenReturn(constants);
        Whitebox.setInternalState(PlatformFactory.class, "serviceLocator", serviceLocator);
        platformFactory = Whitebox.invokeConstructor(PlatformFactory.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullConstructor() {
        PlatformFactory.register(null);
    }

    @Test
    public void testConstructor() {
        IServiceLocator sL = mock(IServiceLocator.class);
        Whitebox.setInternalState(PlatformFactory.class, "serviceLocator", sL);
        PlatformFactory.register(serviceLocator);
        assertEquals(serviceLocator, (IServiceLocator) Whitebox.getInternalState(PlatformFactory.class, "serviceLocator"));
    }

    @Test
    public void testCreatePlatformDarknessMode() throws Exception {
        IPlatform platform = new Platform(serviceLocator, new Point((int) randomX, (int) randomY), sprite);
        IPlatform darkness = new PlatformDarkness(serviceLocator, platform);
        Whitebox.setInternalState(Game.class, "mode", Game.Modes.darkness);

        IPlatform actual = (IPlatform) (Whitebox.invokeMethod(platformFactory, "createPlatform", (int) randomX, (int) randomY));
        assertEquals(darkness.getProps(), actual.getProps());
        assertEquals(darkness.getPoint(), actual.getPoint());
        assertEquals(darkness.getSprite(), actual.getSprite());
        assertEquals(darkness.getBoost(), actual.getBoost(), 0.01);
    }

    @Test
    public void testCreatePlatformTypeNormal() throws Exception {
        IPlatform platform = new Platform(serviceLocator, new Point(0, 0), sprite);

        IPlatform actual = (IPlatform) (Whitebox.invokeMethod(platformFactory, "createPlatform", normalPlatform));
        assertEquals(platform.getProps(), actual.getProps());
        assertEquals(platform.getPoint(), actual.getPoint());
        assertEquals(platform.getSprite(), actual.getSprite());
        assertEquals(platform.getBoost(), actual.getBoost(), 0.01);
    }

    @Test
    public void testCreatePlatformTypeHorizontal() throws Exception {
        IPlatform platform = new Platform(serviceLocator, new Point(0, 0), sprite);
        IPlatform horizontal = new PlatformHorizontal(serviceLocator, platform);

        IPlatform actual = (IPlatform) (Whitebox.invokeMethod(platformFactory, "createPlatform", horizontalMovingPlatform));
        assertEquals(horizontal.getProps(), actual.getProps());
        assertEquals(horizontal.getPoint(), actual.getPoint());
        assertEquals(horizontal.getSprite(), actual.getSprite());
        assertEquals(horizontal.getBoost(), actual.getBoost(), 0.01);
    }

    @Test
    public void testCreatePlatformTypeVertical() throws Exception {
        IPlatform platform = new Platform(serviceLocator, new Point(0, 0), sprite);
        IPlatform vertical = new PlatformVertical(serviceLocator, platform);

        IPlatform actual = (IPlatform) (Whitebox.invokeMethod(platformFactory, "createPlatform", verticalMovingPlatform));
        assertEquals(vertical.getProps(), actual.getProps());
        assertEquals(vertical.getPoint(), actual.getPoint());
        assertEquals(vertical.getSprite(), actual.getSprite());
        assertEquals(vertical.getBoost(), actual.getBoost(), 0.01);
    }

    @Test
    public void testCreatePlatformTypeBreaking() throws Exception {
        IPlatform platform = new Platform(serviceLocator, new Point(0, 0), sprite);
        IPlatform broken = new PlatformBroken(serviceLocator, platform);

        IPlatform actual = (IPlatform) (Whitebox.invokeMethod(platformFactory, "createPlatform", breakingPlatform));
        assertEquals(broken.getProps(), actual.getProps());
        assertEquals(broken.getPoint(), actual.getPoint());
        assertEquals(broken.getSprite(), actual.getSprite());
        assertEquals(broken.getBoost(), actual.getBoost(), 0.01);
    }

    @Test
    public void testCreatePlatformTypeDarkness() throws Exception {
        IPlatform platform = new Platform(serviceLocator, new Point(0, 0), sprite);
        IPlatform darkness = new PlatformDarkness(serviceLocator, platform);

        IPlatform actual = (IPlatform) (Whitebox.invokeMethod(platformFactory, "createPlatform", darknessPlatform));
        assertEquals(darkness.getProps(), actual.getProps());
        assertEquals(darkness.getPoint(), actual.getPoint());
        assertEquals(darkness.getSprite(), actual.getSprite());
        assertEquals(darkness.getBoost(), actual.getBoost(), 0.01);
    }
}
