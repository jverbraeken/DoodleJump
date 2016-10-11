package objects.doodle.DoodleBehavior;

import constants.Constants;
import input.Keys;
import objects.doodles.Doodle;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.SpaceBehavior;
import objects.doodles.DoodleBehavior.UnderwaterBehavior;
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

/**
 * Test class for the RegularBehavior class.
 */
public class UnderwaterBehaviorTest {

    private IServiceLocator serviceLocator;
    private Constants constants;
    private IDoodle doodle;
    private UnderwaterBehavior regular;

    @Before
    public void init() throws Exception {
        serviceLocator = mock(IServiceLocator.class);
        doodle = Mockito.mock(Doodle.class);
        regular = Whitebox.invokeConstructor(UnderwaterBehavior.class, serviceLocator, doodle);
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

