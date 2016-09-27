package objects.doodles.DoodleBehavior;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import objects.doodles.IDoodle;

/**
 * This class describes the behaviour of the doodle.
 */
public interface movementBehavior extends IKeyInputObserver {

    /**
     * Return the vertical speed.
     * @return the speed.
     */
    double getVerticalSpeed();

    /**
     * Set the vertical speed of the doodle.
     * @param vSpeed the new speed.
     */
    void setVerticalSpeed(double vSpeed);

    /**
     * Get the direction we are facing.
     * @return the direction
     */
    Directions getFacing();

    /**
     * Move.
     * @param delta frame duration.
     */
    void move(double delta);

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
