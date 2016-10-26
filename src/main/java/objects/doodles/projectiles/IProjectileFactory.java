package objects.doodles.projectiles;

import system.IFactory;

/**
 * The interface of a ProjectileFactory.
 */
public interface IProjectileFactory extends IFactory {
    /**
     * Create a new RegularProjectile.
     *
     * @param x    The x location.
     * @param y    The y location.
     * @param xDir The speed with which the projectile moves over the x-axis
     * @param yDir the speed with which the projectile moves over the y-axis
     * @return The new RegularProjectile.
     */
    RegularProjectile createRegularProjectile(final int x, final int y, final int xDir, final int yDir);
}