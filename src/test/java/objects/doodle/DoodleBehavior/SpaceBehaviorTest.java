package objects.doodle.DoodleBehavior;

import constants.IConstants;
import input.Keys;
import objects.doodles.Doodle;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.DoodleBehavior.SpaceBehavior;
import objects.doodles.IDoodle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the RegularBehavior class.
 */
public class SpaceBehaviorTest {

    private IServiceLocator serviceLocator = Mockito.mock(IServiceLocator.class);
    private IDoodle doodle = Mockito.mock(Doodle.class);
    private IConstants constants = Mockito.mock(IConstants.class);
    private SpaceBehavior behavior;

    @Before
    public void init() throws Exception {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(constants.getGravityAcceleration()).thenReturn(0d);
        when(doodle.getKeyLeft()).thenReturn(Keys.arrowLeft);
        when(doodle.getKeyRight()).thenReturn(Keys.arrowRight);

        behavior = new SpaceBehavior(serviceLocator, doodle);
    }
    @Test
    public void testApplyGravity() throws Exception {
        Whitebox.invokeMethod(behavior, "applyGravity", 0d);
        verify(serviceLocator, times(1)).getConstants();
        verify(constants, times(1)).getGravityAcceleration();
    }

}

