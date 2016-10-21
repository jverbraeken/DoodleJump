package objects.doodle.DoodleBehavior;

import constants.IConstants;
import input.Keys;
import objects.doodles.DoodleBehavior.MovementBehavior;
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
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the UnderwaterBehavior class.
 */
public class UnderwaterBehaviorTest {

    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private IConstants constants = mock(IConstants.class);
    private IDoodle doodle = mock(IDoodle.class);
    private IPowerup powerup = mock(IPowerup.class);
    private UnderwaterBehavior underwater;

    @Before
    public void init() throws Exception {
        when(constants.getGravityAcceleration()).thenReturn(.5);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(doodle.getPowerup()).thenReturn(powerup);
        when(doodle.getKeys()).thenReturn(new Keys[]{Keys.arrowLeft, Keys.arrowRight});

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
        underwater.keyPress(Keys.arrowLeft);
        underwater.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(underwater.getFacing()));
        assertThat(MovementBehavior.Directions.Right, is(underwater.getMoving()));
        assertThat(Whitebox.getInternalState(underwater, "pressed"), is(true));
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressRightLeft() throws Exception{
        underwater.keyPress(Keys.arrowRight);
        underwater.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(underwater.getFacing()));
        assertThat(MovementBehavior.Directions.Left, is(underwater.getMoving()));
        assertThat(Whitebox.getInternalState(underwater, "pressed"), is(true));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the feild can't be found.
     */
    @Test
    public void testKeyReleaseLeft() throws Exception {
        underwater.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(underwater.getFacing()));
        assertThat(MovementBehavior.Directions.Left, is(underwater.getMoving()));
        underwater.keyRelease(Keys.arrowLeft);
        assertThat(underwater.getMoving(), is(MovementBehavior.Directions.Left));
        assertThat(Whitebox.getInternalState(underwater, "pressed"), is(false));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the feild can't be found.
     */
    @Test
    public void testKeyReleaseRight() throws Exception {
        underwater.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(underwater.getFacing()));
        assertThat(MovementBehavior.Directions.Right, is(underwater.getMoving()));
        underwater.keyRelease(Keys.arrowRight);
        assertThat(underwater.getMoving(), is(MovementBehavior.Directions.Right));
        assertThat(Whitebox.getInternalState(underwater, "pressed"), is(false));
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
        Mockito.verify(doodle, Mockito.times(1)).setSprite(underwater.getFacing(), true);
    }

    /**
     * Tests the move method.
     */
    @Test
    public void testPerformPowerupOnMove() {
        underwater.move(0d);
        Mockito.verify(doodle, Mockito.times(1)).getPowerup();
        Mockito.verify(powerup, Mockito.times(1)).perform(PowerupOccasion.constant);
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

        Mockito.verify(serviceLocator, Mockito.times(1)).getConstants();
        Mockito.verify(constants, Mockito.times(1)).getGravityAcceleration();
        Mockito.verify(doodle, Mockito.times(1)).addYPos(expected);
        assertThat(underwater.getVerticalSpeed(), is(expected));
    }

    @After
    public void cleanUp() {
        serviceLocator = null;
        doodle = null;
    }
}

