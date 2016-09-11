package objects;

import java.awt.*;

/**
 * Created by Nick on 7-9-2016.
 */
public abstract class GameObject implements IGameObject {

    private int xPos;
    private int yPos;
    private double[] hitBox;
    private Object sprite;

    //TODO: implement correct implementation
    public boolean collide(GameObject that) {
        return false;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public double[] getHitBox() {
        return hitBox;
    }

    //TODO: change Object to sprite
    /** {@inheritDoc} */
    public Object getSprite() {
        return sprite;
    }

    //TODO: change to use Graphics (swing?)
    public abstract void paint();

    //TODO: change to support correct implementation
    public abstract void animate();

    //TODO: change to support correct implementation
    public abstract void move();

    //TODO: change to support correct implementation
    public abstract void update();
}
