package objects.enemies;

import logging.ILogger;
import objects.IGameObject;
import system.IServiceLocator;

public final class EnemyBuilder implements IEnemyBuilder {

    /**
     * The service locator for the EnemyBuilder class.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the EnemyBuilder class.
     */
    private static ILogger LOGGER;

    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        EnemyBuilder.serviceLocator = serviceLocator;
        serviceLocator.provide(new EnemyBuilder());
        LOGGER = serviceLocator.getLoggerFactory().createLogger(EnemyBuilder.class);
    }

    private EnemyBuilder() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createEnemy() {
        LOGGER.info("A new Enemy has been created");
        return new Enemy();
    }

}
