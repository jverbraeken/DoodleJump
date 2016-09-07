package objects;

/**
 * Created by Nick on 7-9-2016.
 */
public abstract class GameObject implements IGameObject{

    private double xPos;
    private double yPos;
    private double[] hitBox;
    private Object sprite;

    //TODO: implement correct implementation
    public boolean collide(GameObject A, GameObject B) {
        return false;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
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
