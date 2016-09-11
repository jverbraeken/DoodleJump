package objects;

import java.awt.Graphics;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class AGameObject implements IGameObject {

    private int xPos;
    private int yPos;
    private double[] hitBox;
    private Object sprite;

    //TODO: implement correct implementation
    /** {@inheritDoc} */
    @Override
    public boolean collide(AGameObject that) {
        return false;
    }

    @Override
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    @Override
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    /** {@inheritDoc} */
    @Override
    public int getXPos() {
        return xPos;
    }

    /** {@inheritDoc} */
    @Override
    public int getYPos() {
        return yPos;
    }

    /** {@inheritDoc} */
    @Override
    public double[] getHitBox() {
        return hitBox;
    }

    //TODO: change Object to sprite
    /** {@inheritDoc} */
    @Override
    public Object getSprite() {
        return sprite;
    }

    /** {@inheritDoc} */
    @Override
    public abstract void paint();

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    @Override
    public abstract void animate();

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    @Override
    public abstract void move();

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    @Override
    public abstract void update();
}
