package objects.powerups;

import objects.IGameObject;
import system.IServiceLocator;

public final class PowerupFactory implements IPowerupFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        PowerupFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new PowerupFactory());
    }

    private PowerupFactory() { }

    /** {@inheritDoc} */
    @Override
    public IGameObject createSpring(final int x, final int y) {
        return new Spring(serviceLocator, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public IGameObject createTrampoline(final int x, final int y) {
        return new Trampoline(serviceLocator, x, y);
    }

}
