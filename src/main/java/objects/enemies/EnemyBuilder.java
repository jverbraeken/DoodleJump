package objects.enemies;

import system.IServiceLocator;

/**
 * This is a factory creating enemies.
 */
public final class EnemyBuilder implements IEnemyBuilder {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;

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
        EnemyBuilder.sL = sL;
        EnemyBuilder.sL.provide(new EnemyBuilder());
    }

}
