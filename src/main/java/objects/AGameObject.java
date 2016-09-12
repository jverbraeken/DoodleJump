package objects;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class AGameObject implements IGameObject {

    private int height;
    private double[] hitBox;
    private Object sprite;
    private int width;
    private int xPos;
    private int yPos;

    /** {@inheritDoc} */
    @Override
    public abstract void animate();

    /** {@inheritDoc} */
    @Override
    public void addXPos(int xPos) {
        int current = this.getXPos();
        this.setXPos(current + xPos);
    }

    /** {@inheritDoc} */
    @Override
    public void addYPos(int yPos) {
        int current = this.getYPos();
        this.setYPos(current + yPos);
    }

    @Override
    /** (@inheritDoc} */
    public void collide(IGameObject collidee) { }

    /** {@inheritDoc} */
    @Override
    public double[] getHitBox() {
        return this.hitBox;
    }

    /** {@inheritDoc} */
    @Override
    public int getHeight() {
        return this.height;
    }

    /** {@inheritDoc} */
    @Override
    public Object getSprite() {
        return this.sprite;
    }

    /** {@inheritDoc} */
    @Override
    public int getWidth() {
        return this.width;
    }

    /** {@inheritDoc} */
    @Override
    public int getXPos() { return this.xPos; }

    /** {@inheritDoc} */
    @Override
    public int getYPos() {
        return this.yPos;
    }

    /** {@inheritDoc} */
    @Override
    public abstract void move();

    /** {@inheritDoc} */
    @Override
    public abstract void paint();

    /** {@inheritDoc} */
    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    /** {@inheritDoc} */
    @Override
    public void setWidth(int width) { this.width = width; }

    /** {@inheritDoc} */
    @Override
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    /** {@inheritDoc} */
    @Override
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    /** {@inheritDoc} */
    @Override
    public abstract void update();
}
