package objects.enemies;

import system.IServiceLocator;

/**
 * This is a factory creating enemies.
 */
public final class EnemyBuilder implements IEnemyBuilder {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Prevent instantiations of EnemyBuilder.
     */
    private EnemyBuilder() {
    }

    /**
     * Register the enemy builder into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        EnemyBuilder.serviceLocator = sL;
        EnemyBuilder.serviceLocator.provide(new EnemyBuilder());
    }

}
