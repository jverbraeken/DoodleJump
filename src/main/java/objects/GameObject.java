package objects;

import java.awt.Graphics;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class GameObject implements IGameObject {

    private double xPos;
    private double yPos;
    private double[] hitBox;
    private Object sprite;

    //TODO: implement correct implementation
    /** {@inheritDoc} */
    public boolean collide(GameObject that) {
        return false;
    }

    /** {@inheritDoc} */
    public double getXPos() {
        return xPos;
    }

    /** {@inheritDoc} */
    public double getYPos() {
        return yPos;
    }

    /** {@inheritDoc} */
    public double[] getHitBox() {
        return hitBox;
    }

    //TODO: change Object to sprite
    /** {@inheritDoc} */
    public Object getSprite() {
        return sprite;
    }

    //TODO: change to use Graphics (swing?)
    /** {@inheritDoc} */
    public abstract void paint(Graphics g);

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    public abstract void animate();

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    public abstract void move();

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    public abstract void update();
}
