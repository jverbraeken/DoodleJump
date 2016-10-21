package objects.powerups;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;

/**
 * This class describes the functionality of powerups.
 */
public interface IPowerup extends IGameObject {

    /**
     * Perform the action of the powerup.
     *
     * @param occasion The occasion on which the powerup should perform its action
     */
    void perform(final PowerupOccasion occasion);

    /**
     * Set the x and y position of the powerup that's spawning on a platform.
     * @param powerup a IGameObject that's going to be spawning.
     * @param platform the platform object where the powerup is going to spawn.
     */
    void setPositionOnPlatform(final IGameObject powerup, final IPlatform platform);
}
