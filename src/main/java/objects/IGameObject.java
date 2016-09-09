package objects;

import java.awt.*;

/**
 * Created by Nick on 7-9-2016.
 */
public interface IGameObject {

    public boolean collide(GameObject that);

    public double getXPos();

    public double getYPos();

    public void setXPos(double xPos);

    public void setYPos(double yPos);

    public void addXPos(double extra);

    public void addYPos(double extra);

    public double[] getHitBox();

    //TODO: change Object to sprite

    /**
     * utyu
     * @return erdtf
     */
    public Object getSprite();

    //TODO: change to use Graphics (swing?)
    public void paint(Graphics g);

    //TODO: change to support correct implementation
    public void animate();

    //TODO: change to support correct implementation
    public void move();

    //TODO: change to support correct implementation
    public void update();
}
