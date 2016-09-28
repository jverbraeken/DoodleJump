package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;

/**
 * This class describes the behaviour of the doodle.
 */
public interface IDoodle extends IGameObject, IKeyInputObserver {

    /**
     * Get the vertical speed of the Doodle
     *
     * @return The vertical speed.
     */
    double getVerticalSpeed();

    /**
     * The Doodle collided with a jumpable GameObject.
     *
     * @param jumpable The jumpable GameObject.
     */
    void collide(final IJumpable jumpable);

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
     * Get the score of the Doodle.
     *
     * @return The score of the Doodle.
     */
    double getScore();

}
