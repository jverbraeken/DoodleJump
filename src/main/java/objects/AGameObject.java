package objects;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class AGameObject implements IGameObject {

    /**
     * The height of the game object.
     */
    private int height;
    /**
     * The hitbox of the game object.
     */
    private double[] hitBox;
    /**
     * The sprite of the game object.
     */
    private Object sprite;
    /**
     * The width of the game object.
     */
    private int width;
    /**
     * The position on the x axis of the game object.
     */
    private double xPos;
    /**
     * The position on the y axis of the game object.
     */
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
    public void addXPos(final double x) {
        double current = getXPos();
        this.setXPos(current + x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addYPos(final double y) {
        double current = getYPos();
        setYPos(current + y);
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
    public final double[] getHitBox() {
        return this.hitBox;
    }

    @Override
    public final void setHitBox(final double[] hb) {
        this.hitBox = hb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object getSprite() {
        return this.sprite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getHeight() {
        return this.height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setHeight(final int h) {
        this.height = h;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getWidth() {
        return this.width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setWidth(final int w) {
        this.width = w;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getXPos() {
        return this.xPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setXPos(final double x) {
        this.xPos = x;
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
    public final double getYPos() {
        return this.yPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setYPos(final double y) {
        this.yPos = y;
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
