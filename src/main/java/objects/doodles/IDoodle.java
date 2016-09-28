package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
import objects.powerups.IPassive;

/**
 * This class describes the behaviour of the doodle.
 */
public interface IDoodle extends IGameObject, IKeyInputObserver {

    /**
     * Get the vertical speed of the Doodle.
     *
     * @return The vertical speed.
     */
    double getVerticalSpeed();

    /**
     * The Doodle collides with a jumpable object.
     *
     * @param jumpable The jumpable object.
     */
    void collide(final IJumpable jumpable);

    /**
     * Add a passive item to the Doodle.
     *
     * @param item The item to add as passive.
     */
    void setPassive(final IPassive item);

    /**
     * Removes a passive from the Doodle.
     *
     * @param item The item to remove as passive.
     */
    void removePassive(final IPassive item);

    /**
     * Returns the height of the legs of the Doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     *
     * @return The height of the legs of the Doodle.
     */
    double getLegsHeight();

    /**
     * Set the vertical speed of the Doodle.
     *
     * @param vSpeed the new speed.
     */
    void setVerticalSpeed(final double vSpeed);

    /**
     * Get the socre of the Doodle.
     *
     * @return The score of the Doodle
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
