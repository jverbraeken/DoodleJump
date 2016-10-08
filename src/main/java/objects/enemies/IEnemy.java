package objects.enemies;

import objects.IGameObject;
import objects.IJumpable;

/**
 * This class describes the behavior of an enemy.
 */
public interface IEnemy extends IGameObject, IJumpable {
    /**
     * Apply gravity to the Breaking platform.
     */
    void applyGravity();

    /**
     * Get if the Enemy is killed.
     * @return the attribute killed.
     */
    boolean getKilled();

    /**
     * Set if the Enemy is killed.
     * @param killed a boolean if the Enemy is killed.
     */
     void setKilled(boolean killed);
}
