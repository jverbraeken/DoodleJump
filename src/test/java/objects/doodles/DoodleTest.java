package objects.doodles;

import constants.IConstants;
import input.IInputManager;
import input.Keys;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.IGameObject;
import objects.IJumpable;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.Projectiles.RegularProjectile;
import objects.powerups.APowerup;
import objects.powerups.IPowerup;
import objects.powerups.PowerupOccasion;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import rendering.ICamera;
import rendering.IRenderer;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.World;
import system.Game;
import system.IRenderable;
import system.IServiceLocator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

public class DoodleTest {

    ICamera camera = mock(ICamera.class);
    IConstants constants = mock(IConstants.class);
    IInputManager inputManager = mock(IInputManager.class);
    IJumpable jumpable = mock(IJumpable.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IPowerup somePowerup = mock(IPowerup.class);
    IRenderer renderer = mock(IRenderer.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    MovementBehavior movementBehavior = mock(MovementBehavior.class);
    RegularBehavior regularBehavior = mock(RegularBehavior.class);
    World world = mock(World.class);

    static ISprite spriteLeft1 = mock(ISprite.class);
    static ISprite spriteLeft2 = mock(ISprite.class);
    static ISprite spriteRight1 = mock(ISprite.class);
    static ISprite spriteRight2 = mock(ISprite.class);
    ISprite[] spritesLeft = new ISprite[] { spriteLeft1, spriteLeft2 };
    ISprite[] spritesRight = new ISprite[] { spriteRight1, spriteRight2 };
    IDoodle doodle;
    double jumpableBoost = 10d;
    int spriteHeight = 10;
    int spriteWidth = 10;
    RegularProjectile projectile = mock(RegularProjectile.class);
    List<RegularProjectile> projectiles = new ArrayList<>();

    @Before
    public void init() {
        Whitebox.setInternalState(Game.class, "mode", Game.Modes.regular);

        when(constants.getGravityAcceleration()).thenReturn(1d);
        when(constants.getGameHeight()).thenReturn(1000);
        when(jumpable.getBoost()).thenReturn(jumpableBoost);
        when(loggerFactory.createLogger(Doodle.class)).thenReturn(logger);
        when(loggerFactory.createLogger(ShootingObserver.class)).thenReturn(logger);
        when(renderer.getCamera()).thenReturn(camera);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(serviceLocator.getInputManager()).thenReturn(inputManager);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getRenderer()).thenReturn(renderer);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteLeft1.getHeight()).thenReturn(spriteHeight);
        when(spriteLeft1.getWidth()).thenReturn(spriteWidth);
        when(spriteFactory.getDoodleLeftSprites()).thenReturn(spritesLeft);
        when(spriteFactory.getDoodleRightSprites()).thenReturn(spritesRight);

        doodle = new Doodle(serviceLocator, world);

        Whitebox.setInternalState(doodle, "behavior", regularBehavior);
        Whitebox.setInternalState(regularBehavior, "doodle", doodle);
        Whitebox.setInternalState(regularBehavior, "facing", MovementBehavior.Directions.Left);
        Whitebox.setInternalState(regularBehavior, "serviceLocator", serviceLocator);
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
        Whitebox.setInternalState(doodle, "powerup", somePowerup);
        doodle.collide(jumpable);
        verify(somePowerup, times(1)).perform(PowerupOccasion.collision);
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

    @Test
    public void testSetKeys() {
        doodle.setKeys(Keys.a, Keys.d);
        Keys[] keys = Whitebox.getInternalState(doodle, "keys");
        assertThat(keys[0], is(Keys.a));
        assertThat(keys[1], is(Keys.d));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetKeysNull() {
        doodle.setKeys(null, null);
    }

    @Test
    public void testGetLegsHeight() {
        double actual = Whitebox.getInternalState(Doodle.class, "LEGS_HEIGHT");
        double expected = doodle.getLegsHeight();
        assertThat(expected, is(actual));
    }

    @Test
    public void testGetPowerupFake() {
        IPowerup expected = Whitebox.getInternalState(Doodle.class, "fakePowerup");
        IPowerup actual = doodle.getPowerup();
        assertThat(actual, is(expected));
    }

    @Test
    public void testGetPowerup() {
        Whitebox.setInternalState(doodle, "powerup", somePowerup);
        IPowerup actual = doodle.getPowerup();
        assertThat(actual, is(somePowerup));
    }

    @Test
    public void testSetPowerup() {
        doodle.setPowerup(somePowerup);
        IPowerup actual = Whitebox.getInternalState(doodle, "powerup");
        assertThat(actual, is(somePowerup));
    }

    @Test
    public void testRemovePowerup() {
        Whitebox.setInternalState(doodle, "powerup", somePowerup);
        doodle.removePowerup(somePowerup);
        IPowerup actual = Whitebox.getInternalState(doodle, "powerup");
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testRemoveWrongPowerup() {
        Whitebox.setInternalState(doodle, "powerup", somePowerup);
        doodle.removePowerup(null);
        IPowerup actual = Whitebox.getInternalState(doodle, "powerup");
        assertThat(actual, is(somePowerup));
    }

    @Test
    public void testRemovePowerupGetFakePowerup() {
        Whitebox.setInternalState(doodle, "powerup", somePowerup);
        doodle.removePowerup(somePowerup);
        IPowerup expected = Whitebox.getInternalState(Doodle.class, "fakePowerup");
        IPowerup actual = doodle.getPowerup();
        assertThat(actual, is(expected));
    }

    @Test
    public void testRender() {
        double x = Whitebox.getInternalState(doodle, "xPos");
        double y = Whitebox.getInternalState(doodle, "yPos");
        double scalar = Whitebox.getInternalState(doodle, "spriteScalar");
        int height = (int) scalar * spriteHeight;
        int width = (int) scalar * spriteWidth;

        doodle.render();
        verify(serviceLocator, times(1)).getRenderer();
        verify(renderer, times(1)).drawSprite(spriteLeft1, new Point((int) x, (int) y), width, height);
    }

    @Test
    public void testRenderPowerup() {
        Whitebox.setInternalState(doodle, "powerup", somePowerup);
        doodle.render();
        verify(somePowerup, times(1)).render();
    }

    @Test
    public void testRenderOneProjectile() {
        projectiles.add(projectile);
        Whitebox.setInternalState(doodle, "projectiles", projectiles);

        doodle.render();
        verify(projectile, times(1)).render();
    }

    @Test
    public void testRenderProjectiles() {
        projectiles.add(projectile);
        projectiles.add(projectile);
        Whitebox.setInternalState(doodle, "projectiles", projectiles);

        doodle.render();
        verify(projectile, times(2)).render();
    }

    @Test
    public void testUpdate() {
        doodle.update(0d);
        verify(regularBehavior, times(1)).move(0d);
    }

    @Test
    public void testGetScore() {
        double expected = 10d;
        Whitebox.setInternalState(doodle, "score", expected);
        double actual = doodle.getScore();
        assertThat(actual, is(expected));
    }

    @Test
    public void testIncreaseSpriteScalar() {
        Whitebox.setInternalState(doodle, "spriteScalar", 1d);
        doodle.increaseSpriteScalar(.1d);
        double spriteScalar = Whitebox.getInternalState(doodle, "spriteScalar");
        assertThat(spriteScalar, is(1.1d));
    }

    @Test
    public void testIncreaseSpriteScalarLimit() {
        double max = Whitebox.getInternalState(Doodle.class, "SPRITE_SCALAR_MAX");
        Whitebox.setInternalState(doodle, "spriteScalar", max);
        doodle.increaseSpriteScalar(.1d);

        double spriteScalar = Whitebox.getInternalState(doodle, "spriteScalar");
        assertThat(spriteScalar, is(max));
    }

    @Test
    public void testDecreaseSpriteScalar() {
        Whitebox.setInternalState(doodle, "spriteScalar", 1d);
        doodle.increaseSpriteScalar(-.1d);
        double spriteScalar = Whitebox.getInternalState(doodle, "spriteScalar");
        assertThat(spriteScalar, is(.9d));
    }

    @Test
    public void testDecreaseSpriteScalarLimit() {
        double min = Whitebox.getInternalState(Doodle.class, "SPRITE_SCALAR_MIN");
        Whitebox.setInternalState(doodle, "spriteScalar", min);
        doodle.increaseSpriteScalar(-.1d);

        double spriteScalar = Whitebox.getInternalState(doodle, "spriteScalar");
        assertThat(spriteScalar, is(min));
    }

    @Test
    public void testSetVerticalSpeed() {
        Whitebox.setInternalState(doodle, "behavior", movementBehavior);
        double speed = 0d;
        doodle.setVerticalSpeed(speed);
        verify(movementBehavior, times(1)).setVerticalSpeed(speed);
    }

    @Test
    public void testGetWorld() {
        World actual = doodle.getWorld();
        assertThat(actual, is(world));
    }

    @Test
    public void testKeyPress() {
        Whitebox.setInternalState(doodle, "behavior", movementBehavior);
        doodle.keyPress(Keys.arrowLeft);
        verify(movementBehavior, times(1)).keyPress(Keys.arrowLeft);
    }

    @Test
    public void testKeyRelease() {
        Whitebox.setInternalState(doodle, "behavior", movementBehavior);
        doodle.keyRelease(Keys.arrowLeft);
        verify(movementBehavior, times(1)).keyRelease(Keys.arrowLeft);
    }

    @Test
    public void testAddProjectile() {
        List<IGameObject> expected = new ArrayList<>();
        List<IGameObject> actual = Whitebox.getInternalState(doodle, "projectiles");
        assertThat(actual, is(expected));

        expected.add(projectile);
        doodle.addProjectile(projectile);
        assertThat(actual, is(expected));
    }

    @Test
    public void testRemoveProjectile() {
        projectiles.add(projectile);
        Whitebox.setInternalState(doodle, "projectiles", projectiles);
        doodle.removeProjectile(projectile);
        List<IGameObject> actual = Whitebox.getInternalState(doodle, "projectiles");
        assertThat(actual, is(new ArrayList<>()));
    }

    @Test
    public void testGetProjectilesEmpty() {
        List<IGameObject> actual = doodle.getProjectiles();
        assertThat(actual, is(new ArrayList<>()));
    }

    @Test
    public void testGetProjectilesNotEmpty() {
        projectiles.add(projectile);
        Whitebox.setInternalState(doodle, "projectiles", projectiles);
        List<IGameObject> expected = new ArrayList<>();
        expected.add(projectile);
        List<IGameObject> actual = doodle.getProjectiles();
        assertThat(actual, is(expected));
    }

}
