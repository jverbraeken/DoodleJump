package objects;

import rendering.IDrawable;

import java.util.ArrayList;
import java.util.Set;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IDrawable {

    void addXPos(double xPos);

    void addYPos(double yPos);

    void animate();

    double getBoost();

    Object getSprite();

    double[] getHitBox();

    int getHeight();

    int getWidth();

    double getXPos();

    double getYPos();

    void move();

    void render();

    void setHeight(int height);

    void setWidth(int width);

    void setXPos(double xPos);

    void setYPos(double yPos);

    void setHitBox(double[] hitbox);

    void update();

    /**
     * Checks if the object collides with another {@link IGameObject game object}.
     * @param other The {@link IGameObject game object} to check the collision with
     * @return True if the object collides with the other game object
     */
    boolean collide(IGameObject other);

}
