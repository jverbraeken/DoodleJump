package objects.doodles.Projectiles;

import system.IFactory;

import java.awt.*;

/**
 * The interface of a ProjectileFactory.
 */
public interface IProjectileFactory extends IFactory {
    /**
     * Create a new RegularProjectile.
     *
     * @param point The location.
     * @param xDir  The speed with which the projectile moves over the x-axis
     * @return The new RegularProjectile.
     */
    RegularProjectile createRegularProjectile(final Point point, final int xDir);
}