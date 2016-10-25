package objects.doodles;

import constants.IConstants;
import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.World;
import system.IServiceLocator;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class DoodleTest {

    static IConstants constants = mock(IConstants.class);
    static ILogger logger = mock(ILogger.class);
    static ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    static IServiceLocator serviceLocator = mock(IServiceLocator.class);
    static ISprite spriteLeft1 = mock(ISprite.class);
    static ISprite spriteLeft2 = mock(ISprite.class);
    static ISprite spriteRight1 = mock(ISprite.class);
    static ISprite spriteRight2 = mock(ISprite.class);
    static ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    static World world = mock(World.class);

    static IDoodle doodle;
    static ISprite[] spritesLeft = new ISprite[] { spriteLeft1, spriteLeft2 };
    static ISprite[] spritesRight = new ISprite[] { spriteRight1, spriteRight2 };

    @Before
    public void init() {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(loggerFactory.createLogger(Doodle.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getDoodleLeftSprites()).thenReturn(spritesLeft);
        when(spriteFactory.getDoodleRightSprites()).thenReturn(spritesRight);

        doodle = new Doodle(serviceLocator, world);
    }

    @Test
    public void testIsAlive() {
        Whitebox.setInternalState(doodle, "alive", true);
        boolean actual = doodle.isAlive();
        assertTrue(actual);
    }

    @Test
    public void testIsNotAlive() {
        Whitebox.setInternalState(doodle, "alive", false);
        boolean actual = doodle.isAlive();
        assertFalse(actual);
    }


}
