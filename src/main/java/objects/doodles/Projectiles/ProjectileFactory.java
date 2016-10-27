package objects.doodles.Projectiles;

import system.IServiceLocator;

import java.awt.Point;

/**
 * The ProjectileFactory class, which creates projectiles.
 */
public final class ProjectileFactory implements IProjectileFactory {

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
    public RegularProjectile createRegularProjectile(final Point point, final int xDir, final int yDir) {
        return new RegularProjectile(serviceLocator, point, xDir, yDir);
    }
}