package objects.doodles.Projectiles;

import system.IFactory;

import java.awt.Point;

/**
 * The interface of a ProjectileFactory.
 */
public interface IProjectileFactory extends IFactory {

    /**
     * Create a new RegularProjectile.
     *
     * @param point the location.
     * @return The new RegularProjectile.
     */
    RegularProjectile createRegularProjectile(final Point point, final int direction);

}
