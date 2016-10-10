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
    public IGameObject createOrdinaryEnemy(final int x, final int y) {
        ISprite sprite = serviceLocator.getSpriteFactory().getOrdinaryMonsterSprite();
        logger.info("A new Ordinary Enemy has been created: x = " + x + ", y = " + y);
        return new Enemy(serviceLocator, x, y, sprite);

    }

}
