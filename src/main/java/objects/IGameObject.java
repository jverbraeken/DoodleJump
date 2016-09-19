package objects;

import rendering.IDrawable;

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

    void setHitBox(double[] hitbox);

    int getHeight();

    void setHeight(int height);

    int getWidth();

    void setWidth(int width);

    double getXPos();

    void setXPos(double xPos);

    double getYPos();

    void setYPos(double yPos);

    void move();

    void render();

    void update();

}
