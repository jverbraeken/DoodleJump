package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.powerups.IPassive;

/**
 * This class describes the behaviour of the doodle.
 */
public interface IDoodle extends IGameObject, IKeyInputObserver {

    /**
     * Get the vertical speed of the Doodle
     * @return The vertical speed.
     */
    double getVerticalSpeed();

    /**
     * The Doodle collides with a jumpable object.
     * @param jumpable The jumpable object.
     */
    void collide(IJumpable jumpable);

    /**
     * Add a passive item to the Doodle.
     * @param item The item to add as powerup.
     */
    void setPassive(IPassive item);

    /**
     * Returns the height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     * @return The height of the legs of the doodle
     */
    double getLegsHeight();

    /**
     * Set the vertical speed of the doodle.
     * @param vSpeed the new speed.
     */
    void setVerticalSpeed(double vSpeed);

    /**
     * @return The score of the doodle
     */
    double getScore();

    /**
     * Enum with Directions for the Doodle.
     */
    enum Directions {
        /**
         * The left direction.
         */
        Left,
        /**
         * The right direction.
         */
        Right
    }

}
