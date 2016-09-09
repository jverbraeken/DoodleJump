package objects;

import java.awt.*;

/**
 * Created by Nick on 7-9-2016.
 */
public abstract class GameObject implements IGameObject{

    private double xPos;
    private double yPos;
    private double[] hitBox;
    private Object sprite;

    //TODO: implement correct implementation
    public boolean collide(GameObject that) {
        return false;
    }

    /**
     * Get the X position of the Object.
     *
     * @return The X position of the Object.
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Get the Y position of the Object.
     *
     * @return The Y position of the Object.
     */
    public double getYPos() {
        return yPos;
    }

    /**
     * Set the X position of the Object.
     *
     * @param   xPos    The new X position.
     */
    public void setXPos(double xPos) { this.xPos = xPos; }

    /**
     * Set the Y position of the Object.
     *
     * @param   yPos    The new Y position.
     */
    public void setYPos(double yPos) { this.yPos = yPos; }

    /**
     * Add a value to the X position.
     *
     * @param   extra    The value to add.
     */
    public void addXPos(double extra) {
        double current = this.getXPos();
        this.setXPos(current + extra);
    }

    /**
     * Add a value to the Y position.
     *
     * @param   extra    The value to add.
     */
    public void addYPos(double extra) {
        double current = this.getYPos();
        this.setYPos(current + extra);
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
    public abstract void paint(Graphics g);

    //TODO: change to support correct implementation
    public abstract void animate();

    //TODO: change to support correct implementation
    public abstract void move();

    //TODO: change to support correct implementation
    public abstract void update();
}
