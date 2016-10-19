package objects.doodle.DoodleBehavior;

import constants.Constants;
import constants.IConstants;
import input.Keys;
import objects.doodles.Doodle;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.UnderwaterBehavior;
import objects.doodles.IDoodle;
import objects.powerups.IPowerup;
import objects.powerups.PowerupOccasion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the UnderwaterBehavior class.
 */
public class UnderwaterBehaviorTest {

    private IServiceLocator serviceLocator;
    private IConstants constants;
    private IDoodle doodle;
    private UnderwaterBehavior underwater;
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
        underwater = new UnderwaterBehavior(serviceLocator, doodle);
    }

    /**
     * Tests the getVerticalSpeed method.
     */
    @Test
    public void testGetVerticalSpeed() {
        assertThat(underwater.getVerticalSpeed(), is(0d));
    }

    /**
     * Tests the setVerticalSpeed method.
     */
    @Test
    public void testSetVerticalSpeed() {
        assertThat(underwater.getVerticalSpeed(), is(0d));
        underwater.setVerticalSpeed(5d);
        assertThat(underwater.getVerticalSpeed(), is(2.5d));
    }

    /**
     * Tests the getMoving method.
     */
    @Test
    public void testGetMoving() {
        assertThat(underwater.getMoving(), is(nullValue()));
        underwater.keyPress(Keys.arrowRight);
        assertThat(underwater.getMoving(), is(MovementBehavior.Directions.Right));
    }

    /**
     * Tests the getFacing method.
     */
    @Test
    public void testGetFacing() {
        assertThat(underwater.getFacing(), is(nullValue()));
        underwater.keyPress(Keys.arrowRight);
        assertThat(underwater.getFacing(), is(MovementBehavior.Directions.Right));
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressLeftRight() throws Exception{
        Field pressedField = UnderwaterBehavior.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);
        underwater.keyPress(Keys.arrowLeft);
        underwater.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(underwater.getFacing()));
        assertThat(MovementBehavior.Directions.Right, is(underwater.getMoving()));
        assertThat(pressedField.get(underwater), is(true));
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressRightLeft() throws Exception{
        Field pressedField = UnderwaterBehavior.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);
        underwater.keyPress(Keys.arrowRight);
        underwater.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(underwater.getFacing()));
        assertThat(MovementBehavior.Directions.Left, is(underwater.getMoving()));
        assertThat(pressedField.get(underwater), is(true));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the feild can't be found.
     */
    @Test
    public void testKeyReleaseLeft() throws Exception {
        Field pressedField = UnderwaterBehavior.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);

        underwater.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(underwater.getFacing()));
        assertThat(MovementBehavior.Directions.Left, is(underwater.getMoving()));
        underwater.keyRelease(Keys.arrowLeft);
        assertThat(underwater.getMoving(), is(MovementBehavior.Directions.Left));
        assertThat(pressedField.get(underwater), is(false));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the feild can't be found.
     */
    @Test
    public void testKeyReleaseRight() throws Exception {
        Field pressedField = UnderwaterBehavior.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);

        underwater.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(underwater.getFacing()));
        assertThat(MovementBehavior.Directions.Right, is(underwater.getMoving()));
        underwater.keyRelease(Keys.arrowRight);
        assertThat(underwater.getMoving(), is(MovementBehavior.Directions.Right));
        assertThat(pressedField.get(underwater), is(false));
    }

    /**
     * Tests the animate method when the vertical speed is above the threshold.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void animatePullInLegsTest() throws Exception {
        underwater.setVerticalSpeed(-16);
        Whitebox.invokeMethod(underwater, "animate", 0d);
        Mockito.verify(doodle).setSprite(underwater.getFacing(), true);
    }

    /**
     * Tests the move method.
     */
    @Test
    public void moveTest() {
        underwater.move(0d);
        Mockito.verify(doodle).getPowerup();
        Mockito.verify(powerup).perform(PowerupOccasion.constant);
    }

    /**
     * Tests the moveHorizontally method when moving left.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallyLeftTest() throws Exception {
        Field hSpeedField = UnderwaterBehavior.class.getDeclaredField("hSpeed");
        Field horAccField = UnderwaterBehavior.class.getDeclaredField("HORIZONTAL_ACCELERATION");
        Field relAccField = UnderwaterBehavior.class.getDeclaredField("RELATIVE_SPEED");
        hSpeedField.setAccessible(true);
        horAccField.setAccessible(true);
        relAccField.setAccessible(true);

        double oldHSpeed = (double) hSpeedField.get(underwater);
        double expectedValue = oldHSpeed - (double) relAccField.get(underwater) * (double) horAccField.get(underwater);

        underwater.keyPress(Keys.arrowLeft);
        Whitebox.invokeMethod(underwater, "moveHorizontally", 0d);
        assertThat(hSpeedField.get(underwater), is(expectedValue));
    }

    /**
     * Tests the moveHorizontally method when moving right.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallyRightTest() throws Exception {
        Field hSpeedField = UnderwaterBehavior.class.getDeclaredField("hSpeed");
        Field horAccField = UnderwaterBehavior.class.getDeclaredField("HORIZONTAL_ACCELERATION");
        Field relAccField = UnderwaterBehavior.class.getDeclaredField("RELATIVE_SPEED");
        hSpeedField.setAccessible(true);
        horAccField.setAccessible(true);
        relAccField.setAccessible(true);

        double oldHSpeed = (double) hSpeedField.get(underwater);
        double expectedValue = oldHSpeed + (double) relAccField.get(underwater) * (double) horAccField.get(underwater);

        underwater.keyPress(Keys.arrowRight);
        Whitebox.invokeMethod(underwater, "moveHorizontally", 0d);
        assertThat(hSpeedField.get(underwater), is(expectedValue));
    }

    /**
     * Tests that for the gravity method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testApplyGravity() throws Exception {
        Field relative_gravity = UnderwaterBehavior.class.getDeclaredField("RELATIVE_GRAVITY");
        relative_gravity.setAccessible(true);
        double expected = (double) relative_gravity.get(underwater) * 0.5;
        Whitebox.invokeMethod(underwater, "applyGravity", 0d);

        Mockito.verify(serviceLocator).getConstants();
        Mockito.verify(constants).getGravityAcceleration();
        Mockito.verify(doodle).addYPos(expected);
        assertThat(underwater.getVerticalSpeed(), is(expected));
    }

    @After
    public void cleanUp() {
        serviceLocator = null;
        doodle = null;
    }
}

