package objects;

import objects.doodles.IDoodle;
import rendering.IDrawable;
import resources.sprites.ISprite;

import java.util.ArrayList;
import java.util.Set;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IDrawable {

    void addXPos(double xPos);

    void addYPos(double yPos);

    ISprite getSprite();

    void setSprite(ISprite sprite);

    double[] getHitBox();

    double getXPos();

    double getYPos();

    void render();

    void setXPos(double xPos);

    void setYPos(double yPos);

    void setHitBox(int left, int top, int right, int bottom);

    void update();

    /**
     * Checks if the game object collides with another game object based on their hitboxes.
     * @param gameObject The object that the object could collide with
     * @return True if the game object collide with each other
     */
    boolean checkCollission(IGameObject gameObject);

    void collidesWith(IDoodle doodle);
}
