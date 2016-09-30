package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
import objects.powerups.IPassive;
import resources.sprites.ISprite;

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
     * Get the passive of the Doodle.
     *
     * @return The passive the Doodle currently has. Note that this can be null!
     */
    IPassive getPassive();

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
     * Get the score for this Doodle.
     *
     * @return The score.
     */
    double getScore();

    /**
     * Get the sprite pack of the doodle.
     *
     * @return the sprite pack.
     */
    ISprite[] getSpritePack();

    /**
     * Increase the sprite scalar for the Doodle.
     *
     * @param inc The value to increase by.
     */
    void increaseSpriteScalar(final double inc);

    /**
     * Set the sprite pack of the Doodle.
     *
     * @param doodleSprite the sprites.
     */
    void setSpritePack(final ISprite[] doodleSprite);

    /**
     * Set the vertical speed of the doodle.
     *
     * @param vSpeed the new speed.
     */
    void setVerticalSpeed(final double vSpeed);

}
