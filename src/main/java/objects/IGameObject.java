package objects;

import java.awt.*;

public interface IGameObject {

    boolean collide(GameObject that);

    int getXPos();
    int getYPos();
    void setXPos(int xPos);
    void setYPos(int yPos);

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
