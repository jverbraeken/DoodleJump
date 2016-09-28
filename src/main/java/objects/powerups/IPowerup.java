package objects.powerups;

import objects.IGameObject;

/**
 * This class describes the abstract functionality of powerups.
 */
public interface IPowerup extends IGameObject {

    /**
     * Get the boost provided by the jumpable.
     * @return The boost.
     */
    double getBoost();

}
