package objects;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class AGameObject implements IGameObject {

    private int xPos;
    private int yPos;
    private double[] hitBox;
    private Object sprite;

    @Override
    /** {@inheritDoc} */
    public abstract void animate();

    @Override
    /** {@inheritDoc} */
    public void addXPos(int xPos) {
        int current = this.getXPos();
        this.setXPos(current + xPos);
    }

    @Override
    /** {@inheritDoc} */
    public void addYPos(int yPos) {
        int current = this.getYPos();
        this.setYPos(current + yPos);
    }

    @Override
    /** {@inheritDoc} */
    public double[] getHitBox() {
        return this.hitBox;
    }

    /** {@inheritDoc} */
    @Override
    public Object getSprite() {
        return this.sprite;
    }

    @Override
    /** {@inheritDoc} */
    public int getXPos() {
        return this.xPos;
    }

    @Override
    /** {@inheritDoc} */
    public int getYPos() {
        return this.yPos;
    }

    @Override
    /** {@inheritDoc} */
    public abstract void move();

    @Override
    /** {@inheritDoc} */
    public abstract void paint();

    @Override
    /** {@inheritDoc} */
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    @Override
    /** {@inheritDoc} */
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    @Override
    /** {@inheritDoc} */
    public abstract void update();
}
