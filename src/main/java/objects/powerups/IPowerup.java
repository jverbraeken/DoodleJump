package objects.powerups;

import objects.IGameObject;

/**
 * This class describes the functionality of powerups.
 */
public interface IPowerup extends IGameObject {

    /**
     * Perform the action of the powerup.
     *
     * @param occasion What is the occasion on which the powerup should perform its action.
     */
    void perform(final String occasion);

}
