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
    public double getBoost() {
        return 0d;
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
    public abstract void render();

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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean collide(final IGameObject other) {
        double y = this.getYPos() + this.getHeight();
        if (y > other.getYPos() && y < other.getYPos() + other.getHeight()) {
            double x = this.getXPos() + (this.getWidth() / 2);
            if (x > other.getXPos() && x < other.getXPos() + other.getWidth()) {
                return true;
            }
        }
        return false;
    }
}
