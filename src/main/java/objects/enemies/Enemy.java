package objects.enemies;

import resources.sprites.ISprite;

/**
 * A sample enemy class.
 */
public class Enemy extends AEnemy {

    /**
     * Creates a new enemy and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param x The X-coordinate of the enemy.
     * @param y The Y-coordinate of the enemy.
     * @param sprite The sprite of the enemy.
     */
    public Enemy(final int x, final int y, final ISprite sprite) {
        super(x, y, sprite);
    }

    /** {@inheritDoc} */
    @Override
    public final double getBoost() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public void render() { }

}
