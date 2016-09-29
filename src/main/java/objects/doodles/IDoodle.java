package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;
import resources.sprites.ISprite;

/**
 * This class describes the behaviour of the doodle.
 */
public interface IDoodle extends IGameObject, IKeyInputObserver {

    /**
     * @return The vertical speed of the doodle
     */
    double getVerticalSpeed();

    /**
     * Set the vertical speed of the doodle.
     *
     * @param vSpeed the new speed.
     */
    void setVerticalSpeed(double vSpeed);

    void collide(IJumpable jumpable);

    void collide(IBlock block);

    /**
     * Returns the height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     *
     * @return The height of the legs of the doodle
     */
    double getLegsHeight();

    /**
     * Get the spritepack of the doodle.
     *
     * @return rhe spritepack
     */
    ISprite[] getSpritePack();

    /**
     * Set the sprite pack of the doodle.
     *
     * @param doodleSprite the sprites.
     */
    void setSpritePack(ISprite[] doodleSprite);

    /**
     * @return The score of the doodle.
     */
    double getScore();

}
