package objects.doodles;

import input.IInputManager;
import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.projectiles.IProjectileFactory;
import objects.doodles.projectiles.RegularProjectile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import system.IServiceLocator;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ShootingObserver.class, RegularProjectile.class})
public class ShootingObserverTest {

    IDoodle doodle = mock(IDoodle.class);
    IInputManager inputManager = mock(IInputManager.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IProjectileFactory projectileFactory = mock(IProjectileFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    RegularProjectile projectile = mock(RegularProjectile.class);

    ShootingObserver shootingObserver;

    int direction = 1;
    double doodleX = 10, doodleY = 10;
    double[] hitbox = new double[] { 0, 0, 10, 10 };

    @Before
    public void init() {
        when(doodle.getHitBox()).thenReturn(hitbox);
        when(doodle.getXPos()).thenReturn(doodleX);
        when(doodle.getYPos()).thenReturn(doodleY);
        when(loggerFactory.createLogger(ShootingObserver.class)).thenReturn(logger);
        when(serviceLocator.getInputManager()).thenReturn(inputManager);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getProjectileFactory()).thenReturn(projectileFactory);
        when(projectileFactory.createRegularProjectile(anyObject(), anyInt())).thenReturn(projectile);

        shootingObserver = new ShootingObserver(serviceLocator, doodle);
    }

    @Test
    public void testRegister() {
        shootingObserver.register();
        verify(inputManager, times(1)).addObserver(shootingObserver);
    }

    @Test
    public void testRegisterLog() {
        shootingObserver.register();
        verify(logger, times(1)).info(anyString());
    }

    @Test
    public void testMouseClickedRight() {
        shootingObserver.mouseClicked(0, 0);
        verify(serviceLocator, times(1)).getProjectileFactory();
        verify(projectileFactory, times(1)).createRegularProjectile(anyObject(), anyInt());
    }

    @Test
    public void testMouseClickedLeft() {
        shootingObserver.mouseClicked(0, (int) (2 * doodleY));
        verify(serviceLocator, times(1)).getProjectileFactory();
        verify(projectileFactory, times(1)).createRegularProjectile(anyObject(), anyInt());
    }

    @Test
    public void testMouseClickedLogs() {
        shootingObserver.mouseClicked(0, 0);
        verify(logger, times(1)).info(anyString());
    }

}
