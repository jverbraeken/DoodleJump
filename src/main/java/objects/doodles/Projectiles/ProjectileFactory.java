package objects.doodles.Projectiles;

import logging.ILogger;
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
     * Logger instance for the ProjectileFactory.
     */
    private final ILogger logger;

    /**
     * Prevent instantiations of DoodleFactory.
     */
    private ProjectileFactory() {
        this.logger = ProjectileFactory.serviceLocator.getLoggerFactory().createLogger(ProjectileFactory.class);
    }

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
        ProjectileFactory.serviceLocator.provide(new ProjectileFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegularProjectile createRegularProjectile(final Point point, final int xDir, final int yDir) {
        this.logger.info("Created a new regular projectile");
        return new RegularProjectile(ProjectileFactory.serviceLocator, point, xDir, yDir);
    }

}
