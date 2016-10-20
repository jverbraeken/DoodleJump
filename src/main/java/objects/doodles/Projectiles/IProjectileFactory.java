package objects.doodles.Projectiles;

import system.IFactory;

public interface IProjectileFactory extends IFactory {
    /**
     * Create a new RegularProjectile.
     *
     * @return The new RegularProjectile.
     */
    RegularProjectile createRegularProjectile(final int x, final int y);
}
