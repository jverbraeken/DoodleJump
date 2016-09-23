package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;

/**
 * This class describes the behaviour of the doodle.
 */
public interface IDoodle extends IGameObject, IKeyInputObserver {

    /**
     * check for collisiong between the doodle and the collidee.
     * @param collidee the other object.
     * @return wether we have hit the other object.
     */
    boolean collide(IGameObject collidee);

    /**
     * Set the vertical speed of the doodle.
     * @param vSpeed the new speed.
     */
    void setVerticalSpeed(double vSpeed);

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
