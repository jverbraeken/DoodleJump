package objects.powerups;

import objects.IGameObject;

/**
 * This class describes a passive powerup.
 */
public interface IPassive extends IGameObject {

    /**
     * Apply a constant boost provided by the passive.
     */
    void applyBoost();

    /**
     * Get the boost provided by the passive.
     */
    double getBoost();

    /**
     * Get the type of the passive.
     *
     * @return A PassiveType.
     */
    PassiveType getType();

}
