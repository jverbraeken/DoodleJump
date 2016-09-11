package objects;

import rendering.IDrawable;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IDrawable {

    boolean collide(AGameObject that);

    int getXPos();

    int getYPos();

    void setXPos(int xPos);

    void setYPos(int yPos);

    double[] getHitBox();

    //TODO: change Object to sprite
    Object getSprite();

    //TODO: change to support correct implementation
    void animate();

    //TODO: change to support correct implementation
    void move();

    //TODO: change to support correct implementation
    void update();
}
