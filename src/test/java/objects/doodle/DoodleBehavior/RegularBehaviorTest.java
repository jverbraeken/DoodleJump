package objects.doodle.DoodleBehavior;

import constants.IConstants;
import input.Keys;
import objects.doodles.Doodle;
import objects.doodles.DoodleBehavior.RegularBehavior;
import objects.doodles.IDoodle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Test class for the RegularBehavior class.
 */
public class RegularBehaviorTest {

    private IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private IDoodle doodle = mock(Doodle.class);
    private IConstants constants = mock(IConstants.class);
    private RegularBehavior behavior;

    @Before
    public void init() throws Exception {
        when(serviceLocator.getConstants()).thenReturn(constants);
        when(constants.getGravityAcceleration()).thenReturn(0d);
        when(doodle.getKeyLeft()).thenReturn(Keys.arrowLeft);
        when(doodle.getKeyRight()).thenReturn(Keys.arrowRight);

        behavior = new RegularBehavior(serviceLocator, doodle);
    }

    @Test
    public void testApplyGravity() throws Exception {
        Whitebox.invokeMethod(behavior, "applyGravity", 0d);
        verify(serviceLocator, times(1)).getConstants();
        verify(constants, times(1)).getGravityAcceleration();
    }

}

