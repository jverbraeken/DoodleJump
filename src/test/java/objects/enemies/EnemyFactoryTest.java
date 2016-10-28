package objects.enemies;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.powerups.PowerupFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.*;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EnemyFactory.class, Enemy.class})
public class EnemyFactoryTest {

    Enemy enemy = mock(Enemy.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISprite sprite = mock(ISprite.class);
    ISpriteFactory spriteFactory = mock(ISpriteFactory.class);

    EnemyFactory enemyFactory;

    private static int xPos = 1;
    private static int yPos = 1;
    private static Point point = new Point(xPos, yPos);

    @Before
    public void init() throws Exception {
        when(loggerFactory.createLogger(EnemyFactory.class)).thenReturn(logger);
        when(loggerFactory.createLogger(Enemy.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getOrdinaryMonsterSprite()).thenReturn(sprite);

        whenNew(Point.class).withArguments(xPos, yPos).thenReturn(point);

        Whitebox.setInternalState(EnemyFactory.class, "serviceLocator", serviceLocator);
        enemyFactory = Whitebox.invokeConstructor(EnemyFactory.class);
    }

    @Test
    public void testCreateOrdinaryEnemy() throws Exception {
        whenNew(Enemy.class).withArguments(serviceLocator, point, sprite).thenReturn(enemy);
        enemyFactory.createOrdinaryEnemy(xPos, yPos);
        verifyNew(Enemy.class).withArguments(serviceLocator, point, sprite);
    }

}
