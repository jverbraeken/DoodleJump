package system;

import logging.ILoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ServiceLocatorNoAudio.class)
public class GameTest {

    @Before
    public void init() {

    }

    @Test
    public void testConstructor1() throws Exception {
        mockStatic(ServiceLocatorNoAudio.class);
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        when(serviceLocator.getLoggerFactory()).thenReturn(mock(ILoggerFactory.class));
        when(ServiceLocatorNoAudio.getServiceLocator()).thenReturn(serviceLocator);
        final Game game = Whitebox.invokeConstructor(Game.class);
        assertThat(Whitebox.getInternalState(Game.class, "serviceLocator"), is(serviceLocator));
    }
}
