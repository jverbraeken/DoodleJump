package objects;

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

    @Override
    /** {@inheritDoc} */
    public abstract void animate();

    @Override
    /** {@inheritDoc} */
    public void addXPos(double xPos) {
        double current = this.getXPos();
        this.setXPos(current + xPos);
    }

    @Override
    /** {@inheritDoc} */
    public void addYPos(double yPos) {
        double current = this.getYPos();
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
    public int getHeight() {
        return this.height;
    }

    @Override
    /** {@inheritDoc} */
    public int getWidth() {
        return this.width;
    }

    @Override
    /** {@inheritDoc} */
    public double getXPos() {
        return this.xPos;
    }

    @Override
    /** {@inheritDoc} */
    public double getYPos() {
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
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    /** {@inheritDoc} */
    public void setWidth(int width) { this.width = width; }

    @Override
    /** {@inheritDoc} */
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    @Override
    /** {@inheritDoc} */
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    @Override
    /** {@inheritDoc} */
    public abstract void update();
}
