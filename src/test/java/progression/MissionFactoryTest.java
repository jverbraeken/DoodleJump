package progression;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.powerups.IPowerupFactory;
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

    private Callable action = mock(Callable.class);
    private ILogger logger = mock(ILogger.class);
    private ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private IMissionFactory missionFactory;
    private IPowerupFactory powerupFactory = mock(IPowerupFactory.class);
    private IProgressionManager progressionManager = mock(IProgressionManager.class);
    private IServiceLocator serviceLocator = mock(IServiceLocator.class);

    @Before
    public void init() throws Exception {
        when(loggerFactory.createLogger(MissionFactory.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getPowerupFactory()).thenReturn(powerupFactory);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);

        Whitebox.setInternalState(MissionFactory.class, "serviceLocator", serviceLocator);
        missionFactory = Whitebox.invokeConstructor(MissionFactory.class);
    }

    @Test
    public void testCreateMissionJumpOnSpring() throws Exception {
        final int maximumTimes = 42;
        Mission result = missionFactory.createMission(MissionType.jumpOnSpring, ProgressionObservers.jumpable, maximumTimes, action);

        assertThat(result.getMaximumTimes(), is(maximumTimes));
    }

}
