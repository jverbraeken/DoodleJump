package objects.powerups;

import objects.IGameObject;

/**
 * This class describes a passive powerup.
 */
public interface IPassive extends IGameObject {

    /**
     * Get the boost provided by the passive.
     *
     * @return The boost provided by the passive.
     */
    double getBoost();

    /**
     * Get the type of the passive.
     *
     * @return A PassiveType.
     */
    PassiveType getType();

}
