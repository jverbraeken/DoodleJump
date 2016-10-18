package progression;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.util.concurrent.Callable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;

public class MissionFactoryTest {
    private IMissionFactory missionFactory;

    @Before
    public void init() throws Exception {
        missionFactory = Whitebox.invokeConstructor(MissionFactory.class);
    }

    @Test
    public void testCreateMissionJumpOnSpring() throws Exception {
        final Callable action = mock(Callable.class);
        final int maximumTimes = 42;
        final IServiceLocator serviceLocator = mock(IServiceLocator.class);
        final IProgressionManager progressionManager = mock(IProgressionManager.class);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        Whitebox.setInternalState(MissionFactory.class, "serviceLocator", serviceLocator);
        Mission result = missionFactory.createMission(MissionType.jumpOnSpring, maximumTimes, action);

        assertThat(result.getMaximumTimes(), is(maximumTimes));
        verifyPrivate(progressionManager, times(1)).invoke("addObserver", eq(ProgressionObservers.spring), anyObject());
    }
}
