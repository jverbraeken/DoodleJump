package objects.powerups;

import system.IServiceLocator;

public final class PowerupFactory implements IPowerupFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        PowerupFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new PowerupFactory());
    }

    private PowerupFactory() { }

    public IPowerup createPowerup() {
        return new Trampoline(serviceLocator);
    }

}
