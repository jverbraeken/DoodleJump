package objects.enemies;

import objects.IGameObject;

public interface IEnemyBuilder {

    /**
     * Create a new enemy. NOT READY FOR USE YET.
     *
     * @return A new enemy.
     */
    IGameObject createEnemy();

}
