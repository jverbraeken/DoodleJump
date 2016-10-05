package objects.powerups;

import objects.IGameObject;

/**
 * This class describes the functionality of powerups.
 */
public interface IPowerup extends IGameObject {

    /**
     * Perform the action of the powerup.
     */
    void perform(final String occasion);

}
