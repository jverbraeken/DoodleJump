package objects;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IRenderable;
import system.IUpdatable;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IRenderable, IUpdatable {

    /**
     * Change the x position of the game object.
     *
     * @param xPos the amount to add.
     */
    void addXPos(final double xPos);

    /**
     * Change the y position of the game object.
     *
     * @param yPos the amount to add.
     */
    void addYPos(final double yPos);

    /**
     * Retrieve the sprite of the game object.
     *
     * @return the sprite.
     */
    ISprite getSprite();

    /**
     * Sets the sprite of the game object.
     *
     * @param sprite The new sprite of the game object
     */
    void setSprite(final ISprite sprite);

    /**
     * Retrieve the hitbox of the game object.
     *
     * @return the hitbox.
     */
    double[] getHitBox();

    /**
     * Retrieve the x position of the game object.
     *
     * @return the x position.
     */
    double getXPos();

    /**
     * Set the x position of the game object.
     *
     * @param xPos the to be x position.
     */
    void setXPos(final double xPos);

    /**
     * Retrieve the y position of the game object.
     *
     * @return the y position.
     */
    double getYPos();

    /**
     * Set the y position of the game object.
     *
     * @param yPos the to be y position.
     */
    void setYPos(final double yPos);

    /**
     * Set the hitbox of the game object.
     *
     * @param left The margin between the X-coordinate and the left side of the hitbox.
     * @param top The margin between the Y-coordinate and the top side of the hitbox.
     * @param right The distance between the X-coordinate and the right side of the hitbox.
     * @param bottom The distance between the Y-coordinate and the bottom side of the hitbox.
     */
    void setHitBox(final int left, final int top, final int right, final int bottom);

    /**
     * Checks if the game object collides with another game object based on their hit boxes.
     *
     * @param gameObject The object that the object could collide with
     * @return True if the game object collide with each other
     */
    boolean checkCollision(final IGameObject gameObject);

    /**
     * Check if the GameObjects collides with a Doodle.
     * @param doodle The Doodle to check.
     */
    void collidesWith(final IDoodle doodle);

}
