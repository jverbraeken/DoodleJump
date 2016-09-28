package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;

/**
 * This class describes the behaviour of the doodle.
 */
public interface IDoodle extends IGameObject, IKeyInputObserver {

    double getVerticalSpeed();

    void collide(IJumpable jumpable);

    void collide(IBlock block);

    /**
     * Adds a passive item to the Doodle.
     *
     * @param have Does the doodle have spring shoes?
     */
    void setSpringShoes(boolean have);

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
