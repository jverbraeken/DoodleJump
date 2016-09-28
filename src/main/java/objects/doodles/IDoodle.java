package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
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
     * Returns the height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     *
     * @return The height of the legs of the doodle
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
     * Set the sprite pack of the doodle.
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
