package objects.doodle.DoodleBehavior;

import constants.Constants;
import constants.IConstants;
import input.Keys;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the RegularBehavior class.
 */
public class SpaceBehaviorTest {

    private IServiceLocator serviceLocator;
    private Constants constants;
    private IDoodle doodle;
    private SpaceBehavior regular;

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
        regular = Whitebox.invokeConstructor(SpaceBehavior.class, serviceLocator, doodle);
        regular.keyPress(Keys.arrowLeft);
        assertEquals(MovementBehavior.Directions.Left, Whitebox.getInternalState(regular, "facing"));
        assertEquals(MovementBehavior.Directions.Left, Whitebox.getInternalState(regular, "moving"));
        regular.keyPress(Keys.arrowRight);
        assertEquals(MovementBehavior.Directions.Right, Whitebox.getInternalState(regular, "facing"));
        assertEquals(MovementBehavior.Directions.Right, Whitebox.getInternalState(regular, "moving"));
        assertEquals(true, regular.getPressed());
    }

    /**
     * Tests if the key press is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyPressRightLeft() throws Exception{
        regular = Whitebox.invokeConstructor(SpaceBehavior.class, serviceLocator, doodle);
        regular.keyPress(Keys.arrowRight);
        assertEquals(MovementBehavior.Directions.Right, Whitebox.getInternalState(regular, "facing"));
        assertEquals(MovementBehavior.Directions.Right, Whitebox.getInternalState(regular, "moving"));
        regular.keyPress(Keys.arrowLeft);
        assertEquals(MovementBehavior.Directions.Left, Whitebox.getInternalState(regular, "facing"));
        assertEquals(MovementBehavior.Directions.Left, Whitebox.getInternalState(regular, "moving"));
        assertEquals(true, Whitebox.getInternalState(regular, "pressed"));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyReleaseLeft() throws Exception{
        regular = Whitebox.invokeConstructor(SpaceBehavior.class, serviceLocator, doodle);
        regular.keyPress(Keys.arrowLeft);
        assertEquals(MovementBehavior.Directions.Left, Whitebox.getInternalState(regular, "facing"));
        assertEquals(MovementBehavior.Directions.Left, Whitebox.getInternalState(regular, "moving"));
        regular.keyRelease(Keys.arrowLeft);
        assertEquals(false, Whitebox.getInternalState(regular, "pressed"));
    }

    /**
     * Tests if the key release is processed.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testKeyReleaseRight() throws Exception{
        regular = Whitebox.invokeConstructor(SpaceBehavior.class, serviceLocator, doodle);
        regular.keyPress(Keys.arrowRight);
        assertEquals(MovementBehavior.Directions.Right, Whitebox.getInternalState(regular, "facing"));
        assertEquals(MovementBehavior.Directions.Right, Whitebox.getInternalState(regular, "moving"));
        regular.keyRelease(Keys.arrowRight);
        assertEquals(false, Whitebox.getInternalState(regular, "pressed"));
    }

    /**
     * Tests that for the gravity method is called.
     *
     * @throws Exception throws an exception when the private constructor can not be called or when an exception is thrown
     *                   in the constructor.
     */
    @Test
    public void testApplyGravity() throws Exception {
        java.lang.reflect.Method m = Whitebox.getMethod(RegularBehavior.class, "applyGravity", double.class);
        assertNotNull(m);
    }


    @After
    public void cleanUp() {
        serviceLocator = null;
        doodle = null;
    }
}

