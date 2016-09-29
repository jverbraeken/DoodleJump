package objects.enemies;

import logging.ILogger;
import objects.IGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Standard implementation of the EnemyBuilder. Used to generate enemies.
 */
public final class EnemyBuilder implements IEnemyBuilder {

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
    private EnemyBuilder() {
        logger = serviceLocator.getLoggerFactory().createLogger(EnemyBuilder.class);
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        EnemyBuilder.serviceLocator = sL;
        EnemyBuilder.serviceLocator.provide(new EnemyBuilder());
    }

    /** {@inheritDoc} */
    @Override
    public IGameObject createEnemy(final int x, final int y, final ISprite sprite) {
        logger.info("A new Enemy has been created: x = " + x + ", y = " + y + " sprite = " + sprite.toString());
        return new Enemy(x, y, sprite);
    }

}
