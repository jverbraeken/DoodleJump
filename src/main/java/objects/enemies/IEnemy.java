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
     * Get if the Enemy is alive.
     * @return the attribute alive.
     */
    boolean isAlive();

    /**
     * Set if the Enemy is alive.
     * @param alive a boolean if the Enemy is alive.
     */
     void setAlive(boolean alive);

    /**
     * Returns the offSet of this Enemy.
     * @return the attribute offSet.
     */
    int getOffSet();

    /**
     * Returns the vSpeed of this Enemy.
     * @return the attribute vSpeed.
     */
    double getVerticalSpeed();
}
