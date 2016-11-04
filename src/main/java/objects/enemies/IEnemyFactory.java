package objects.enemies;

import objects.IGameObject;

/**
 * Interface for an EnemyBuilder.
 */
public interface IEnemyFactory {

    /**
     * Create a new enemy.
     *
     * @param type The type of the enemy
     * @param x The X position for the Enemy.
     * @param y The Y position for the Enemy.
     * @return A new enemy.
     */
    IGameObject createEnemy(final Enemies type, final int x, final int y);

}
