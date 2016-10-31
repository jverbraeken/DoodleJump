package objects.doodles;

import constants.IConstants;
import input.Keys;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.IJumpable;
import objects.doodles.doodle_behavior.MovementBehavior;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class StartScreenDoodleTest {

    IConstants constants = mock(IConstants.class);
    IJumpable jumpable = mock(IJumpable.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    MovementBehavior behavior = mock(MovementBehavior.class);

    static ISprite spriteLeft1 = Mockito.mock(ISprite.class);
    static ISprite spriteLeft2 = Mockito.mock(ISprite.class);
    static ISprite spriteRight1 = Mockito.mock(ISprite.class);
    static ISprite spriteRight2 = Mockito.mock(ISprite.class);

    StartScreenDoodle doodle;
    double jumpableBoost = 10d;
    ISprite[] sprites = new ISprite[]{spriteLeft1, spriteLeft2, spriteRight1, spriteRight2};

    @Before
    public void init() {
        when(constants.getGameHeight()).thenReturn(100);
        when(constants.getGameWidth()).thenReturn(100);
        when(jumpable.getBoost()).thenReturn(jumpableBoost);
        when(loggerFactory.createLogger(Doodle.class)).thenReturn(logger);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getGreenDoodleSprites()).thenReturn(sprites);

        doodle = new StartScreenDoodle(serviceLocator, sprites);
    }

    @Test
    public void testCollide() {
        Whitebox.setInternalState(doodle, behavior);
        double reduction = Whitebox.getInternalState(StartScreenDoodle.class, "BOOST_REDUCTION");
        doodle.collide(jumpable);
        verify(behavior, times(1)).setVerticalSpeed(jumpableBoost * reduction);
    }

    @Test
    public void testKeyPress() {
        doodle.keyPress(Keys.arrowLeft);
        assertTrue(true); // No crash
    }

    @Test
    public void testKeyRelease() {
        doodle.keyRelease(Keys.arrowLeft);
        assertTrue(true); // No crash
    }

}
