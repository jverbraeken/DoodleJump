package objects;

import java.util.ArrayList;
import java.util.Set;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class AGameObject implements IGameObject {

    private int height;
    private Double[] hitBox;
    private Object sprite;
    private int width;
    private double xPos;
    private double yPos;

    /**
     * {@inheritDoc}
     */
    public abstract void animate();

    /**
     * {@inheritDoc}
     */
    public void addXPos(double xPos) {
        double current = this.getXPos();
        this.setXPos(current + xPos);
    }

    /**
     * {@inheritDoc}
     */
    public void addYPos(double yPos) {
        double current = this.getYPos();
        this.setYPos(current + yPos);
    }

    /**
     * {@inheritDoc}
     */
    public Double[] getHitBox() {
        return this.hitBox;
    }

    public void setHitBox(Double[] hitbox) {
        this.hitBox = hitbox;
    }

    /**
     * {@inheritDoc}
     */
    public Object getSprite() {
        return this.sprite;
    }

    /**
     * {@inheritDoc}
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * {@inheritDoc}
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * {@inheritDoc}
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * {@inheritDoc}
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * {@inheritDoc}
     */
    public double getXPos() {
        return this.xPos;
    }

    /**
     * {@inheritDoc}
     */
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * {@inheritDoc}
     */
    public double getYPos() {
        return this.yPos;
    }

    /**
     * {@inheritDoc}
     */
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    /**
     * {@inheritDoc}
     */
    public abstract void move();

    /**
     * {@inheritDoc}
     */
    public abstract void paint();

    /**
     * {@inheritDoc}
     */
    public abstract void update();
}
