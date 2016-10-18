package objects.doodle.DoodleBehavior;

import constants.Constants;
import constants.IConstants;
import input.Keys;
import objects.IGameObject;
import objects.blocks.Block;
import objects.doodles.Doodle;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.SpaceBehavior;
import objects.doodles.IDoodle;
import objects.enemies.Enemy;
import objects.powerups.IPowerup;
import objects.powerups.PowerupOccasion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.InvokeMethod;
import org.mockito.Mockito;
import org.objectweb.asm.commons.Method;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import scenes.World;
import system.IServiceLocator;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the RegularBehavior class.
 */
public class RegularBehaviorTest {

    private IServiceLocator serviceLocator;
    private IDoodle doodle;
    private RegularBehavior regular;
    private IConstants constants;
    private IPowerup powerup;

    @Before
    public void init() throws Exception {
        constants = mock(IConstants.class);
        serviceLocator = mock(IServiceLocator.class);
        when(constants.getGravityAcceleration()).thenReturn(.5);
        when(serviceLocator.getConstants()).thenReturn(constants);
        doodle = mock(IDoodle.class);
        Keys[] keys = new Keys[]{Keys.arrowLeft, Keys.arrowRight};
        powerup = mock(IPowerup.class);
        when(doodle.getPowerup()).thenReturn(powerup);
        when(doodle.getKeys()).thenReturn(keys);
        regular = new RegularBehavior(serviceLocator, doodle);
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
     @Test
     public void testKeyPressLeftRight() throws Exception{
        regular.keyPress(Keys.arrowLeft);
        regular.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(regular.getFacing()));
        assertThat(MovementBehavior.Directions.Right, is(regular.getMoving()));
        }

     /**
      * Tests if the key press is processed.
      *
      * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
      *                   in the constructor.
      */
    @Test
    public void testKeyPressRightLeft() throws Exception{
        regular.keyPress(Keys.arrowRight);
        regular.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(regular.getFacing()));
        assertThat(MovementBehavior.Directions.Left, is(regular.getMoving()));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyReleaseLeft() {
        regular.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(regular.getFacing()));
        assertThat(MovementBehavior.Directions.Left, is(regular.getMoving()));
        regular.keyRelease(Keys.arrowLeft);
        assertThat(regular.getMoving(), is(nullValue()));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyReleaseRight() {
        regular.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(regular.getFacing()));
        assertThat(MovementBehavior.Directions.Right, is(regular.getMoving()));
        regular.keyRelease(Keys.arrowRight);
        assertThat(regular.getMoving(), is(nullValue()));
    }

    /**
     * Tests that for the gravity method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testApplyGravity() throws Exception {
        Whitebox.invokeMethod(regular, "applyGravity", 0d);

        Mockito.verify(serviceLocator).getConstants();
        Mockito.verify(constants).getGravityAcceleration();
        assertThat(regular.getVerticalSpeed(), is(.5));
    }

    /**
     * Tests the getVerticalSpeed method.
     */
    @Test
    public void testGetVerticalSpeed() {
        assertThat(regular.getVerticalSpeed(), is(0d));
    }

    /**
     * Tests the setVerticalSpeed method.
     */
    @Test
    public void testSetVerticalSpeed() {
        assertThat(regular.getVerticalSpeed(), is(0d));
        regular.setVerticalSpeed(5d);
        assertThat(regular.getVerticalSpeed(), is(5d));
    }

    /**
     * Tests the getMoving method.
     */
    @Test
    public void testGetMoving() {
        assertThat(regular.getMoving(), is(nullValue()));
        regular.keyPress(Keys.arrowRight);
        assertThat(regular.getMoving(), is(MovementBehavior.Directions.Right));
    }

    /**
     * Tests the getFacing method.
     */
    @Test
    public void testGetFacing() {
        assertThat(regular.getFacing(), is(nullValue()));
        regular.keyPress(Keys.arrowRight);
        assertThat(regular.getFacing(), is(MovementBehavior.Directions.Right));
    }

    /**
     * Tests the move method.
     */
    @Test
    public void moveTest() {
        regular.move(0d);
        Mockito.verify(doodle).getPowerup();
        Mockito.verify(powerup).perform(PowerupOccasion.constant);
    }

    /**
     * Tests the animate method when the vertical speed is above the threshold.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void animatePullInLegsTest() throws Exception {
        regular.setVerticalSpeed(-16);
        Whitebox.invokeMethod(regular, "animate", 0d);
        Mockito.verify(doodle).setSprite(regular.getFacing(), true);
    }

    /**
     * Tests the moveHorizontally method when moving left.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallyLeftTest() throws Exception {
        Field hSpeedField = RegularBehavior.class.getDeclaredField("hSpeed");
        Field horAccField = RegularBehavior.class.getDeclaredField("HORIZONTAL_ACCELERATION");
        hSpeedField.setAccessible(true);
        horAccField.setAccessible(true);

        double oldHSpeed = (double) hSpeedField.get(regular);
        double expectedValue = oldHSpeed - (double) horAccField.get(regular);

        regular.keyPress(Keys.arrowLeft);
        Whitebox.invokeMethod(regular, "moveHorizontally", 0d);
        assertThat(hSpeedField.get(regular), is(expectedValue));
    }

    /**
     * Tests the moveHorizontally method when moving right.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallyRightTest() throws Exception {
        Field hSpeedField = RegularBehavior.class.getDeclaredField("hSpeed");
        Field horAccField = RegularBehavior.class.getDeclaredField("HORIZONTAL_ACCELERATION");
        hSpeedField.setAccessible(true);
        horAccField.setAccessible(true);

        double oldHSpeed = (double) hSpeedField.get(regular);
        double expectedValue = oldHSpeed + (double) horAccField.get(regular);

        regular.keyPress(Keys.arrowRight);
        Whitebox.invokeMethod(regular, "moveHorizontally", 0d);
        assertThat(hSpeedField.get(regular), is(expectedValue));
    }

    /**
     * Tests the moveHorizontally method when moving right.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallySlowdownLeftTest() throws Exception {
        Field hSpeedField = RegularBehavior.class.getDeclaredField("hSpeed");
        Field horAccField = RegularBehavior.class.getDeclaredField("HORIZONTAL_ACCELERATION");
        hSpeedField.setAccessible(true);
        horAccField.setAccessible(true);

        double oldHSpeed = (double) hSpeedField.get(regular);

        regular.keyPress(Keys.arrowLeft);
        Whitebox.invokeMethod(regular, "moveHorizontally", 0d);
        regular.keyRelease(Keys.arrowLeft);
        Whitebox.invokeMethod(regular, "moveHorizontally", 0d);
        assertThat(hSpeedField.get(regular), is(oldHSpeed));
    }

    /**
     * Tests the moveHorizontally method when moving right.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallySlowdownRightTest() throws Exception {
        Field hSpeedField = RegularBehavior.class.getDeclaredField("hSpeed");
        Field horAccField = RegularBehavior.class.getDeclaredField("HORIZONTAL_ACCELERATION");
        hSpeedField.setAccessible(true);
        horAccField.setAccessible(true);

        double oldHSpeed = (double) hSpeedField.get(regular);

        regular.keyPress(Keys.arrowRight);
        Whitebox.invokeMethod(regular, "moveHorizontally", 0d);
        regular.keyRelease(Keys.arrowRight);
        Whitebox.invokeMethod(regular, "moveHorizontally", 0d);
        assertThat(hSpeedField.get(regular), is(oldHSpeed));
    }

    @After
    public void cleanUp() {
        serviceLocator = null;
        doodle = null;
    }
}

