package objects.enemies;

import resources.sprites.ISprite;
import system.IServiceLocator;

public class Enemy extends AEnemy {
    /**
     * Creates a new enemy and determines its hitbox by using the sprites dimensions automatically.
     * @param sL The service locator
     * @param x The X-coordinate of the enemy
     * @param y The Y-coordinate of the enemy
     * @param sprite The sprite of the enemy
     */
    public Enemy(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite);
    }

    @Override
    public double getBoost() {
        return 0;
    }

    @Override
    public void render() {

    }
}
