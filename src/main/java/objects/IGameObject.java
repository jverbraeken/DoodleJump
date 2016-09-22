package objects;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IRenderable;
import system.IUpdatable;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IDrawable, IRenderable, IUpdatable {

    void addXPos(double xPos);

    void addYPos(double yPos);

    ISprite getSprite();

    void setSprite(ISprite sprite);

    double[] getHitBox();

    double getXPos();

    void setXPos(double xPos);

    double getYPos();

    void setYPos(double yPos);

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
