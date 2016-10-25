package objects.doodles;

import constants.IConstants;
import input.Keys;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.IJumpable;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.powerups.APowerup;
import objects.powerups.IPowerup;
import objects.powerups.PowerupOccasion;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.World;
import system.Game;
import system.IServiceLocator;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

public class DoodleTest {

    IConstants constants = mock(IConstants.class);
    IJumpable jumpable = mock(IJumpable.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    IPowerup collisionPowerup = mock(collisionPowerup.class);
    MovementBehavior movementBehavior = mock(MovementBehavior.class);
    RegularBehavior regularBehavior = mock(RegularBehavior.class);
    World world = mock(World.class);

    static IDoodle doodle;
    static double jumpableBoost = 10d;

    static ISprite spriteLeft1 = mock(ISprite.class);
    static ISprite spriteLeft2 = mock(ISprite.class);
    static ISprite spriteRight1 = mock(ISprite.class);
    static ISprite spriteRight2 = mock(ISprite.class);
    static ISprite[] spritesLeft = new ISprite[] { spriteLeft1, spriteLeft2 };
    static ISprite[] spritesRight = new ISprite[] { spriteRight1, spriteRight2 };

    @Before
    public void init() {
        Whitebox.setInternalState(Game.class, "mode", Game.Modes.regular);

        when(serviceLocator.getConstants()).thenReturn(constants);
        when(jumpable.getBoost()).thenReturn(jumpableBoost);
        when(loggerFactory.createLogger(Doodle.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getDoodleLeftSprites()).thenReturn(spritesLeft);
        when(spriteFactory.getDoodleRightSprites()).thenReturn(spritesRight);

        doodle = new Doodle(serviceLocator, world);
        Whitebox.setInternalState(doodle, "behavior", regularBehavior);
    }

    @Test
    public void testSetDeath() {
        doodle.setAlive(false);
        boolean alive = Whitebox.getInternalState(doodle, "alive");
        assertFalse(alive);
    }

    @Test
    public void testJumpableCollide() {
        Whitebox.setInternalState(doodle, "behavior", movementBehavior);
        doodle.collide(jumpable);
        verify(movementBehavior, times(1)).setVerticalSpeed(jumpableBoost);
    }

    @Test
    public void testJumpableCollidePerformPowerup() {
        Whitebox.setInternalState(doodle, "powerup", collisionPowerup);
        doodle.collide(jumpable);
        verify(collisionPowerup, times(1)).perform(PowerupOccasion.collision);
    }

    @Test
    public void testCollidesWith() {
        doodle.collidesWith(null);
    }

    @Test
    public void testFacing() {
        Whitebox.setInternalState(regularBehavior, "facing", MovementBehavior.Directions.Left);
        MovementBehavior.Directions facing = doodle.getFacing();
        assertThat(facing, is(MovementBehavior.Directions.Left));
    }

    @Test
    public void testGetKeyLeft() {
        Keys[] keys = Whitebox.getInternalState(doodle, "keys");
        Keys left = doodle.getKeyLeft();
        assertThat(left, is(keys[0]));
    }

    @Test
    public void testGetKeyRight() {
        Keys[] keys = Whitebox.getInternalState(doodle, "keys");
        Keys right = doodle.getKeyRight();
        assertThat(right, is(keys[1]));
    }

    /**
     * Internally used powerup classes.
     */
    private class collisionPowerup extends APowerup implements IPowerup {
        public collisionPowerup(IServiceLocator sL, int x, int y, ISprite sprite, Class<?> powerup) {
            super(sL, x, y, sprite, powerup);
        }

        @Override
        public void collidesWith(IDoodle doodle) {
        }

        @Override
        public void render() {
        }
    }

}
