package objects.doodle.DoodleBehavior;

import constants.IConstants;
import input.Keys;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.UnderwaterBehavior;
import objects.doodles.IDoodle;
import objects.powerups.IPowerup;
import objects.powerups.PowerupOccasion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
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
    private UnderwaterBehavior behavior;

    @Before
    public void init() throws Exception {
        when(constants.getGravityAcceleration()).thenReturn(.5);
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(doodle.getPowerup()).thenReturn(powerup);
        when(doodle.getKeyLeft()).thenReturn(Keys.arrowLeft);
        when(doodle.getKeyRight()).thenReturn(Keys.arrowRight);

        behavior = new UnderwaterBehavior(serviceLocator, doodle);
    }

    /**
     * Tests the getVerticalSpeed method.
     */
    @Test
    public void testGetVerticalSpeed() {
        assertThat(behavior.getVerticalSpeed(), is(0d));
    }

    /**
     * Tests the setVerticalSpeed method.
     */
    @Test
    public void testSetVerticalSpeed() {
        assertThat(behavior.getVerticalSpeed(), is(0d));
        behavior.setVerticalSpeed(5d);
        assertThat(behavior.getVerticalSpeed(), is(2.5d));
    }

    /**
     * Tests the getFacing method.
     */
    @Test
    public void testGetFacing() {
        behavior.keyPress(Keys.arrowRight);
        assertThat(behavior.getFacing(), is(MovementBehavior.Directions.Right));
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressLeftRight() throws Exception {
        behavior.keyPress(Keys.arrowLeft);
        behavior.keyPress(Keys.arrowRight);
        assertThat(MovementBehavior.Directions.Right, is(behavior.getFacing()));

        boolean movingLeft = Whitebox.getInternalState(behavior, "movingLeft");
        boolean movingRight = Whitebox.getInternalState(behavior, "movingRight");
        assertThat(movingLeft, is(false));
        assertThat(movingRight, is(true));
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressRightLeft() throws Exception {
        behavior.keyPress(Keys.arrowRight);
        behavior.keyPress(Keys.arrowLeft);
        assertThat(MovementBehavior.Directions.Left, is(behavior.getFacing()));

        boolean movingLeft = Whitebox.getInternalState(behavior, "movingLeft");
        boolean movingRight = Whitebox.getInternalState(behavior, "movingRight");
        assertThat(movingLeft, is(true));
        assertThat(movingRight, is(false));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the feild can't be found.
     */
    @Test
    public void testKeyReleaseLeft() throws Exception {
        behavior.keyPress(Keys.arrowLeft);
        behavior.keyRelease(Keys.arrowLeft);

        boolean movingLeft = Whitebox.getInternalState(behavior, "movingLeft");
        boolean movingRight = Whitebox.getInternalState(behavior, "movingRight");
        assertThat(movingLeft, is(false));
        assertThat(movingRight, is(false));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the feild can't be found.
     */
    @Test
    public void testKeyReleaseRight() throws Exception {
        behavior.keyPress(Keys.arrowRight);
        behavior.keyRelease(Keys.arrowRight);

        boolean movingLeft = Whitebox.getInternalState(behavior, "movingLeft");
        boolean movingRight = Whitebox.getInternalState(behavior, "movingRight");
        assertThat(movingLeft, is(false));
        assertThat(movingRight, is(false));
    }

    /**
     * Tests the animate method when the vertical speed is above the threshold.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void animateCallsUpdateActiveSpriteTest() throws Exception {
        behavior.setVerticalSpeed(-16);
        Whitebox.invokeMethod(behavior, "animate", 0d);
        verify(doodle, Mockito.times(1)).updateActiveSprite();
    }

    /**
     * Tests the move method.
     */
    @Test
    public void testPerformPowerupOnMove() {
        behavior.move(0d);
        verify(doodle, Mockito.times(1)).getPowerup();
        verify(powerup, Mockito.times(1)).perform(PowerupOccasion.constant);
    }

    /**
     * Tests the moveHorizontally method when moving left.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallyLeftTest() throws Exception {
        Whitebox.setInternalState(behavior, "movingLeft", true);
        Whitebox.invokeMethod(behavior, "moveHorizontally", 1d);
        double hSpeed = Whitebox.getInternalState(behavior, "hSpeed");
        assertTrue(hSpeed < 0);
    }

    /**
     * Tests the moveHorizontally method when moving right.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *           in the constructor.
     */
    @Test
    public void moveHorizontallyRightTest() throws Exception {
        Whitebox.setInternalState(behavior, "movingRight", true);
        Whitebox.invokeMethod(behavior, "moveHorizontally", 1d);
        double hSpeed = Whitebox.getInternalState(behavior, "hSpeed");
        assertTrue(hSpeed > 0);
    }

    @Test
    public void testApplyGravity() throws Exception {
        Whitebox.invokeMethod(behavior, "applyGravity", 0d);
        verify(serviceLocator, Mockito.times(1)).getConstants();
        verify(constants, Mockito.times(1)).getGravityAcceleration();
    }

}

