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
     *
     * @param platform the platform object where the powerup is going to spawn
     */
    void setPositionOnPlatform(final IPlatform platform);

    /**
     * End the activity of the current powerup.
     */
    void endPowerup();

}
