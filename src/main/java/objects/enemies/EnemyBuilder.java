package objects.enemies;

import logging.ILogger;
import objects.IGameObject;
import system.IServiceLocator;

public final class EnemyBuilder implements IEnemyBuilder {

    /**
     * The logger for the EnemyBuilder class.
     */
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        EnemyBuilder.serviceLocator = serviceLocator;
        serviceLocator.provide(new EnemyBuilder());
    }

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private EnemyBuilder() {
        LOGGER = serviceLocator.getLoggerFactory().createLogger(EnemyBuilder.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createEnemy() {
        LOGGER.info("A new Enemy has been created");
        return new Enemy();
    }

}
