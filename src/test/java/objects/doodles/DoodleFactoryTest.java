package objects.doodles;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.doodle_behavior.MovementBehavior;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import scenes.World;
import system.IServiceLocator;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DoodleFactory.class, Doodle.class})
public class DoodleFactoryTest {

    Doodle doodle = mock(Doodle.class);
    MovementBehavior behavior = mock(MovementBehavior.class);
    StartScreenDoodle ssDoodle = mock(StartScreenDoodle.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    World world = mock(World.class);

    IDoodleFactory doodleFactory;

    @Before
    public void init() throws Exception {
        when(loggerFactory.createLogger(DoodleFactory.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);

        Whitebox.setInternalState(DoodleFactory.class, "serviceLocator", serviceLocator);
        doodleFactory = Whitebox.invokeConstructor(DoodleFactory.class);

        Whitebox.setInternalState(doodle, "behavior", behavior);
    }

    @Test
    public void testCreateDoodle() throws Exception {
        whenNew(Doodle.class).withArguments(serviceLocator, world).thenReturn(doodle);
        doodleFactory.createDoodle(world, DoodleColors.green);
        verifyNew(Doodle.class).withArguments(serviceLocator, world);
        verify(doodle, times(1)).setVerticalSpeed(anyInt());
    }

    @Test
    public void testCreateStartScreenDoodle() throws Exception {
        whenNew(StartScreenDoodle.class).withArguments(serviceLocator).thenReturn(ssDoodle);
        doodleFactory.createStartScreenDoodle();
        verifyNew(StartScreenDoodle.class).withArguments(serviceLocator);
    }

}
