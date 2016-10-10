package objects.enemies;

import objects.IGameObject;

/**
 * Interface for an EnemyBuilder.
 */
public interface IEnemyBuilder {

    /**
     * Create a new enemy.
     *
     * @param x The X position for the Enemy.
     * @param y The Y position for the Enemy.
     * @return A new enemy.
     */
    IGameObject createOrdinaryEnemy(final int x, final int y);

}
