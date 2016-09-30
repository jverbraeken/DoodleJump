package objects.powerups;

import objects.IGameObject;

/**
 * This class describes a passive powerup.
 */
public interface IPassive extends IGameObject {

    /**
     * Get the boost provided by the jumpable.
     *
     * @return The boost.
     */
    void applyBoost();

}
