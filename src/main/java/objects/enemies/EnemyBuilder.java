package objects.enemies;

import system.IServiceLocator;

public final class EnemyBuilder implements IEnemyBuilder {

    private static transient IServiceLocator serviceLocator;

    private EnemyBuilder() {
    }

    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        EnemyBuilder.serviceLocator = serviceLocator;
        serviceLocator.provide(new EnemyBuilder());
    }

}
