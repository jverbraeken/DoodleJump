package objects.enemies;

import objects.IGameObject;
import resources.sprites.ISprite;

/**
 * Interface for an EnemyBuilder.
 */
public interface IEnemyBuilder {

    /**
     * Create a new enemy. NOT READY FOR USE YET.
     *
     * @param x The X position for the Enemy.
     * @param y The Y position for the Enemy.
     * @param sprite The sprite for the Enemy.
     * @return A new enemy.
     */
    IGameObject createEnemy(final int x, final int y, final ISprite sprite);

}
