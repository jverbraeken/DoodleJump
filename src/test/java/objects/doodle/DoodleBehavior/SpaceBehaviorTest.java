package objects.doodle.DoodleBehavior;

import constants.Constants;
import constants.IConstants;
import input.Keys;
import objects.doodles.Doodle;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.SpaceBehavior;
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
 * Test class for the SpaceBehavior class.
 */
public class SpaceBehaviorTest {

    private IServiceLocator serviceLocator;
    private IConstants constants;
    private IDoodle doodle;
    private SpaceBehavior space;
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
        space = new SpaceBehavior(serviceLocator, doodle);
    }

    /**
     * Tests the getVerticalSpeed method.
     */
    @Test
    public void testGetVerticalSpeed() {
        assertThat(space.getVerticalSpeed(), is(0d));
    }

    /**
     * Tests the setVerticalSpeed method.
     */
    @Test
    public void testSetVerticalSpeed() {
        assertThat(space.getVerticalSpeed(), is(0d));
        space.setVerticalSpeed(5d);
        assertThat(space.getVerticalSpeed(), is(2.5d));
    }

    /**
     * Tests the getMoving method.
     */
    @Test
    public void testGetMoving() {
        assertThat(space.getMoving(), is(nullValue()));
        space.keyPress(Keys.arrowRight);
        assertThat(space.getMoving(), is(MovementBehavior.Directions.Right));
    }

    /**
     * Tests the getFacing method.
     */
    @Test
    public void testGetFacing() {
        assertThat(space.getFacing(), is(nullValue()));
        space.keyPress(Keys.arrowRight);
        assertThat(space.getFacing(), is(MovementBehavior.Directions.Right));
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressLeftRight() throws Exception{
        Field pressedField = SpaceBehavior.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);
        space.keyPress(Keys.arrowLeft);
        space.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(space.getFacing()));
        assertThat(MovementBehavior.Directions.Right, is(space.getMoving()));
        assertThat(pressedField.get(space), is(true));
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressRightLeft() throws Exception{
        Field pressedField = SpaceBehavior.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);
        space.keyPress(Keys.arrowRight);
        space.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(space.getFacing()));
        assertThat(MovementBehavior.Directions.Left, is(space.getMoving()));
        assertThat(pressedField.get(space), is(true));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the feild can't be found.
     */
    @Test
    public void testKeyReleaseLeft() throws Exception {
        Field pressedField = SpaceBehavior.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);

        space.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(space.getFacing()));
        assertThat(MovementBehavior.Directions.Left, is(space.getMoving()));
        space.keyRelease(Keys.arrowLeft);
        assertThat(space.getMoving(), is(MovementBehavior.Directions.Left));
        assertThat(pressedField.get(space), is(false));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the feild can't be found.
     */
    @Test
    public void testKeyReleaseRight() throws Exception {
        Field pressedField = SpaceBehavior.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);

        space.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(space.getFacing()));
        assertThat(MovementBehavior.Directions.Right, is(space.getMoving()));
        space.keyRelease(Keys.arrowRight);
        assertThat(space.getMoving(), is(MovementBehavior.Directions.Right));
        assertThat(pressedField.get(space), is(false));
    }

    /**
     * Tests the animate method when the vertical speed is above the threshold.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void animatePullInLegsTest() throws Exception {
        space.setVerticalSpeed(-16);
        Whitebox.invokeMethod(space, "animate", 0d);
        Mockito.verify(doodle).setSprite(space.getFacing(), true);
    }

    /**
     * Tests the move method.
     */
    @Test
    public void moveTest() {
        space.move(0d);
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
        Field hSpeedField = SpaceBehavior.class.getDeclaredField("hSpeed");
        Field horAccField = SpaceBehavior.class.getDeclaredField("HORIZONTAL_ACCELERATION");
        Field relAccField = SpaceBehavior.class.getDeclaredField("RELATIVE_SPEED");
        hSpeedField.setAccessible(true);
        horAccField.setAccessible(true);
        relAccField.setAccessible(true);

        double oldHSpeed = (double) hSpeedField.get(space);
        double expectedValue = oldHSpeed - (double) relAccField.get(space) * (double) relAccField.get(space) * (double) horAccField.get(space);

        space.keyPress(Keys.arrowLeft);
        Whitebox.invokeMethod(space, "moveHorizontally", 0d);
        assertThat(hSpeedField.get(space), is(expectedValue));
    }

    /**
     * Tests the moveHorizontally method when moving right.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallyRightTest() throws Exception {
        Field hSpeedField = SpaceBehavior.class.getDeclaredField("hSpeed");
        Field horAccField = SpaceBehavior.class.getDeclaredField("HORIZONTAL_ACCELERATION");
        Field relAccField = SpaceBehavior.class.getDeclaredField("RELATIVE_SPEED");
        hSpeedField.setAccessible(true);
        horAccField.setAccessible(true);
        relAccField.setAccessible(true);

        double oldHSpeed = (double) hSpeedField.get(space);
        double expectedValue = oldHSpeed + (double) relAccField.get(space) * (double) relAccField.get(space) * (double) horAccField.get(space);

        space.keyPress(Keys.arrowRight);
        Whitebox.invokeMethod(space, "moveHorizontally", 0d);
        assertThat(hSpeedField.get(space), is(expectedValue));
    }

    /**
     * Tests that for the gravity method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testApplyGravity() throws Exception {
        Field relative_gravity = SpaceBehavior.class.getDeclaredField("RELATIVE_GRAVITY");
        relative_gravity.setAccessible(true);
        double expected = (double) relative_gravity.get(space) * 0.5;
        Whitebox.invokeMethod(space, "applyGravity", 0d);

        Mockito.verify(serviceLocator).getConstants();
        Mockito.verify(constants).getGravityAcceleration();
        Mockito.verify(doodle).addYPos(expected);
        assertThat(space.getVerticalSpeed(), is(expected));
    }


    @After
    public void cleanUp() {
        serviceLocator = null;
        doodle = null;
    }
}

