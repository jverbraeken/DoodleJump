package objects.doodles.Projectiles;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.DoodleBehavior.RegularBehavior;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import system.IServiceLocator;

import java.awt.*;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProjectileFactory.class, RegularProjectile.class})
public class ProjectileFactoryTest {

    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    Point point = mock(Point.class);
    RegularProjectile regularProjectile = mock(RegularProjectile.class);

    IProjectileFactory projectileFactory;

    int xPos = 10, yPos = 10;

    @Before
    public void init() throws Exception {
        when(loggerFactory.createLogger(ProjectileFactory.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);

        Whitebox.setInternalState(ProjectileFactory.class, "serviceLocator", serviceLocator);
        projectileFactory = Whitebox.invokeConstructor(ProjectileFactory.class);
    }

    @Test
    public void testCreateRegularProjectile() throws Exception {
        whenNew(RegularProjectile.class).withArguments(serviceLocator, point, xPos, yPos).thenReturn(regularProjectile);
        projectileFactory.createRegularProjectile(point, xPos, yPos);
        verifyNew(RegularProjectile.class).withArguments(serviceLocator, point, xPos, yPos);
    }

}
