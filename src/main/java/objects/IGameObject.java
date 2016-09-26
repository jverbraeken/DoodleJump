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
     * @param xPos the amount to add.
     */
    void addXPos(double xPos);

    /**
     * Change the y position of the game object.
     * @param yPos the amount to add.
     */
    void addYPos(double yPos);

    /**
     * Retrieve the sprite of the game object.
     * @return the sprite.
     */
    ISprite getSprite();

    /**
     * Sets the sprite of the game object.
     * @param sprite The new sprite of the game object
     */
    void setSprite(ISprite sprite);

    /**
     * Retrieve the hitbox of the game object.
     * @return the hitbox.
     */
    double[] getHitBox();

    /**
     * Retrieve the x position of the game object.
     * @return the x position.
     */
    double getXPos();

    /**
     * Set the x position of the game object.
     * @param xPos the to be x position.
     */
    void setXPos(double xPos);

    /**
     * Retrieve the y position of the game object.
     * @return the y position.
     */
    double getYPos();

    /**
     * Set the y position of the game object.
     * @param yPos the to be y position.
     */
    void setYPos(double yPos);

    /**
     * Set the hitbox of the game object.
     * @param left The margin between the X-coordinate and the left side of the hitbox
     *             @param top The margin between the Y-coordinate and the top side of the hitbox
     * @param right The distancce between the X-coordinate and the right side of the hitbox
     *             @param bottom The distance between the Y-coordinate and the bottom side of the hitbox
     */
    void setHitBox(int left, int top, int right, int bottom);

    /**
     * Checks if the game object collides with another game object based on their hitboxes.
     *
     * @param gameObject The object that the object could collide with
     * @return True if the game object collide with each other
     */
    boolean checkCollission(IGameObject gameObject);

    void collidesWith(IDoodle doodle);
}
