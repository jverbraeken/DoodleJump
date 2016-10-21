/**
 * The projectiles package.
 */
package objects.doodles.Projectiles;

import system.IFactory;

/**
 * The interface of a ProjectileFactory.
 */
public interface IProjectileFactory extends IFactory {
    /**
     * Create a new RegularProjectile.
     *
     * @param x the x location.
     * @param y the y location.
     * @return The new RegularProjectile.
     */
    RegularProjectile createRegularProjectile(final int x, final int y);
}
