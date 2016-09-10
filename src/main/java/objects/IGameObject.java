package objects;

import java.awt.Graphics;

/**
 * The interface implemented by {@link GameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject {

    boolean collide(GameObject that);

    double getXPos();

    double getYPos();

    double[] getHitBox();

    //TODO: change Object to sprite

    Object getSprite();

    //TODO: change to use Graphics (swing?)
    void paint(Graphics g);

    //TODO: change to support correct implementation
    void animate();

    //TODO: change to support correct implementation
    void move();

    //TODO: change to support correct implementation
    void update();
}
