package objects.enemies;

import logging.ILogger;
import objects.IGameObject;
import resources.animations.IAnimation;
import system.IServiceLocator;

import java.awt.Point;

/**
 * Standard implementation of the EnemyBuilder. Used to generate enemies.
 */
public final class EnemyFactory implements IEnemyFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the EnemyBuilder class.
     */
    private final ILogger logger;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private EnemyFactory() {
        logger = serviceLocator.getLoggerFactory().createLogger(EnemyFactory.class);
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        EnemyFactory.serviceLocator = sL;
        EnemyFactory.serviceLocator.provide(new EnemyFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createEnemy(final Enemies type, final int x, final int y) {
        logger.info("A new Ordinary Enemy has been created: x = " + x + ", y = " + y);
        final Point point = new Point(x, y);
        return new Enemy(serviceLocator, point, type);
    }

}
