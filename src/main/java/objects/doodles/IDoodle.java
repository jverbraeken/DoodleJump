package objects.doodles;

import input.IKeyInputObserver;
import input.Keys;
import objects.IGameObject;
import objects.IJumpable;
import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.powerups.IPowerup;

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
     * Get the passive of the Doodle.
     *
     * @return The passive the Doodle currently has. Note that this can be null!
     */
    IPowerup getPowerup();

    /**
     * Add a passive item to the Doodle.
     *
     * @param item The item to add as passive.
     */
    void setPowerup(final IPowerup item);

    /**
     * Removes a passive from the Doodle.
     *
     * @param item The item to remove as passive.
     */
    void removePowerup(final IPowerup item);

    /**
     * Returns the height of the legs of the Doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     *
     * @return The height of the legs of the Doodle.
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
     * Increase the sprite scalar for the Doodle.
     *
     * @param inc The value to increase by.
     */
    void increaseSpriteScalar(final double inc);

    /**
     * Registers its button to the {@link input.IInputManager input manager}.
     */
    void register();

    /**
     * Deregisters its button from the {@link input.IInputManager input manager}.
     */
    void deregister();

    /**
<<<<<<< HEAD
     * Returns true if the doodle is alive.
     * @return the boolean alive.
     */
    boolean isAlive();

    /**
     * Sets the variable alive to alive.
     * @param alive a boolean if the doodle is dead or alive.
     */
    void setAlive(boolean alive);

    /**
     * Get the keys for the Doodle.
     *
     * @return An array of Keys.
     */
    Keys[] getKeys();

    /**
     * Set the keys the Doodle react to.
     *
     * @param left The key to move to the left.
     * @param right The key to move to the right.
     */
    void setKeys(final Keys left, final Keys right);

}
