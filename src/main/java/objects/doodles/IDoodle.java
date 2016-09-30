package objects.doodles;

import input.IInputManager;
import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
import objects.doodles.DoodleBehavior.MovementBehavior;

/**
 * This class describes the behaviour of the doodle.
 */
public interface IDoodle extends IGameObject, IKeyInputObserver {

    /**
     * Get the vertical speed of the Doodle.
     *
     * @return The vertical speed
     */
    double getVerticalSpeed();

    /**
     * Set the vertical speed of the doodle.
     *
     * @param vSpeed the new speed
     */
    void setVerticalSpeed(final double vSpeed);

    /**
     * The Doodle collides with a jumpable object.
     *
     * @param jumpable The jumpable object
     */
    void collide(final IJumpable jumpable);

    /**
     * Returns the height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     *
     * @return The height of the legs of the doodle
     */
    double getLegsHeight();

    /**
     * Get the score for this Doodle.
     *
     * @return The score
     */
    double getScore();

    /**
     * Set the sprite pack of the doodle.
     *
     * @param direction The direction is Doodle is going to
     * @param falling   True if the doodle is going down
     */
    void setSprite(final MovementBehavior.Directions direction, final boolean falling);

    /**
     * Registers its button to the {@link IInputManager input manager}.
     */
    void register();

    /**
     * Deregisters its button from the {@link IInputManager input manager}.
     */
    void deregister();

}
