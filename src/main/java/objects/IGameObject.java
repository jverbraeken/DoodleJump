package objects;

import rendering.IDrawable;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IDrawable {

    void addXPos(int xPos);

    void addYPos(int yPos);

    void animate();

    void collide(IGameObject collidee);

    Object getSprite();

    double[] getHitBox();

    int getXPos();

    int getYPos();

    void move();

    void paint();

    void setXPos(int xPos);

    void setYPos(int yPos);

    void update();
}
