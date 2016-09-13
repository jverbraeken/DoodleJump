package objects;

import java.util.ArrayList;
import java.util.Set;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class AGameObject implements IGameObject {

    private int height;
    private double[] hitBox;
    private Object sprite;
    private int width;
    private double xPos;
    private double yPos;

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void animate();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addXPos(double xPos) {
        double current = this.getXPos();
        this.setXPos(current + xPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addYPos(double yPos) {
        double current = this.getYPos();
        this.setYPos(current + yPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getHitBox() {
        return this.hitBox;
    }

    @Override
    public void setHitBox(double[] hitbox) {
        this.hitBox = hitbox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getSprite() {
        return this.sprite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return this.height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return this.width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public double getXPos() {
        return this.xPos;
    }

    /** {@inheritDoc} */
    @Override
    public abstract void render();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getYPos() {
        return this.yPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void move();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void update();
}
