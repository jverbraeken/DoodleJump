package objects.powerups;

import objects.IGameObject;
import system.IServiceLocator;

/**
 * This is a factory creating powerups.
 */
public final class PowerupFactory implements IPowerupFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;

    /**
     * Prevent instantiations of PowerupFactory.
     */
    private PowerupFactory() {
    }

    /**
     * Register the powerup factory into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        PowerupFactory.sL = sL;
        PowerupFactory.sL.provide(new PowerupFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createSpring(final int x, final int y) {
        return new Spring(sL, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createTrampoline(final int x, final int y) {
        return new Trampoline(sL, x, y);
    }

}
