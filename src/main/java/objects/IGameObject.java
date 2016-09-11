package objects;

import rendering.IDrawable;

import java.awt.*;

public interface IGameObject extends IDrawable {

    boolean collide(GameObject that);

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
