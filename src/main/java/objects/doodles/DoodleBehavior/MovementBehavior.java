package objects.doodles.DoodleBehavior;

import input.IKeyInputObserver;

/**
 * This class describes the behaviour of the doodle.
 */
public interface MovementBehavior extends IKeyInputObserver {

    /**
     * Update the actions of the Doodle.
     */
    void updateActions();

    /**
     * Return the vertical speed.
     *
     * @return the speed.
     */
    double getVerticalSpeed();

    /**
     * Set the vertical speed of the doodle.
     *
     * @param vSpeed the new speed.
     */
    void setVerticalSpeed(final double vSpeed);

    /**
     * Get the direction we are facing.
     *
     * @return the direction
     */
    Directions getFacing();

    /**
     * Move.
     * @param delta frame duration.
     */
    void move(final double delta);

    /**
     * Get the jumping threshold according to the behaviour.
     * @return A double representing the jumping threshold.
     */
    double getJumpingThreshold();

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
