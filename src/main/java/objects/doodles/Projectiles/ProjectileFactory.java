package objects.doodles.Projectiles;

import system.IServiceLocator;

public class ProjectileFactory implements IProjectileFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Prevent instantiations of DoodleFactory.
     */
    private ProjectileFactory() { }

    /**
     * Register the doodle factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        ProjectileFactory.serviceLocator = sL;
        sL.provide(new ProjectileFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegularProjectile createRegularProjectile(final int x, final int y) {
        return new RegularProjectile(serviceLocator, x, y);
    }
}
