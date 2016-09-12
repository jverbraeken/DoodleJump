package objects;

import rendering.IDrawable;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IDrawable {

    void addXPos(double xPos);

    void addYPos(double yPos);

    void animate();

    Object getSprite();

    double[] getHitBox();

    int getHeight();

    int getWidth();

    double getXPos();

    double getYPos();

    void move();

    void setHeight(int height);

    void setWidth(int width);

    void setXPos(double xPos);

    void setYPos(double yPos);

    void update();
}
