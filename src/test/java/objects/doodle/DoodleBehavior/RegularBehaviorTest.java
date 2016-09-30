package objects.doodle.DoodleBehavior;

import input.Keys;
import objects.IGameObject;
import objects.blocks.Block;
import objects.doodles.Doodle;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.SpaceBehavior;
import objects.doodles.IDoodle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the RegularBehavior class.
 */
public class RegularBehaviorTest {

    private IServiceLocator serviceLocator;
    private IDoodle doodle;
    private RegularBehavior regular;

    @Before
    public void init() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
        doodle = Mockito.mock(Doodle.class);
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressLeftRight() throws Exception{
        regular = Whitebox.invokeConstructor(RegularBehavior.class, serviceLocator, doodle);
        regular.keyPress(Keys.arrowLeft);
        regular.keyPress(Keys.arrowRight);
        assertEquals(MovementBehavior.Directions.Right, regular.getFacing());
        assertEquals(MovementBehavior.Directions.Right, regular.getMoving());
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressRightLeft() throws Exception{
        regular = Whitebox.invokeConstructor(RegularBehavior.class, serviceLocator, doodle);
        regular.keyPress(Keys.arrowRight);
        regular.keyPress(Keys.arrowLeft);
        assertEquals(MovementBehavior.Directions.Left, regular.getFacing());
        assertEquals(MovementBehavior.Directions.Left, regular.getMoving());
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyReleaseLeft() throws Exception {
        regular = Whitebox.invokeConstructor(RegularBehavior.class, serviceLocator, doodle);
        regular.keyPress(Keys.arrowLeft);
        assertEquals(MovementBehavior.Directions.Left, regular.getFacing());
        assertEquals(MovementBehavior.Directions.Left, regular.getMoving());
        regular.keyRelease(Keys.arrowLeft);
        assertEquals(null, regular.getMoving());
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyReleaseRight() throws NullPointerException {
        try {
            regular = Whitebox.invokeConstructor(RegularBehavior.class, serviceLocator, doodle);
            regular.keyPress(Keys.arrowRight);
            assertEquals(MovementBehavior.Directions.Right, regular.getFacing());
            assertEquals(MovementBehavior.Directions.Right, regular.getMoving());
            regular.keyRelease(Keys.arrowRight);
            regular.getMoving();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests that for each element in the set of IGameObjects the render in their own class is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testApplyGravity() throws Exception {
        regular = Whitebox.invokeConstructor(RegularBehavior.class, serviceLocator, doodle);
        when(serviceLocator.getConstants().getGravityAcceleration()).thenCallRealMethod();
        regular.setVerticalSpeed(10d);
        double expected = 10d + serviceLocator.getConstants().getGravityAcceleration();
        regular.move(0d);
        verify(doodle).addYPos(expected);
    }

    @After
    public void cleanUp() {
        serviceLocator = null;
        doodle = null;
    }
}

