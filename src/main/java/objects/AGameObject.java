package objects;

import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class AGameObject implements IGameObject {

    protected static IServiceLocator serviceLocator;

    public static final transient int HITBOX_LEFT = 0;
    public static final transient int HITBOX_RIGHT = 1;
    public static final transient int HITBOX_TOP = 2;
    public static final transient int HITBOX_BOTTOM = 3;
    private final double[] hitBox = new double[4];
    private ISprite sprite;
    /**
     * The position on the x axis of the game object.
     */
    private double xPos;
    /**
     * The position on the y axis of the game object.
     */
    private double yPos;

    /**
     * Creates a new game object and determines its hitbox by using the sprites dimensions automatically.
     * @param x The X-coordinate of the game object
     * @param y The Y-coordinate of the game object
     * @param sprite The sprite of the game object. Can be {null} when the object is a {@link objects.blocks.IBlock block}
     */
    public AGameObject(final IServiceLocator sL, int x, int y, ISprite sprite) {
        AGameObject.serviceLocator = sL;
        setXPos(x);
        setYPos(y);
        if (sprite == null) {
            //TODO This is not so awesome
            setHitBox(x, y, sL.getConstants().getGameWidth(), Integer.MAX_VALUE);
        } else {
            setHitBox(0, 0, sprite.getWidth(), sprite.getHeight());
            setSprite(sprite);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addXPos(final double x) {
        double current = getXPos();
        this.setXPos(current + x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addYPos(final double y) {
        double current = getYPos();
        setYPos(current + y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double[] getHitBox() {
        return this.hitBox;
    }

    @Override
    public final void setHitBox(int left, int top, int right, int bottom) {
        this.hitBox[HITBOX_LEFT] = left;
        this.hitBox[HITBOX_TOP] = top;
        this.hitBox[HITBOX_RIGHT] = right;
        this.hitBox[HITBOX_BOTTOM] = bottom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ISprite getSprite() {
        return this.sprite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setSprite(ISprite sprite) {
        this.sprite = sprite;
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
    public final void setXPos(double xPos) {
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
    public boolean checkCollission(IGameObject gameObject) {
        if (gameObject == null) {
            throw new IllegalArgumentException("gameObject cannot be null");
        }

        // If one of these boolean turns false there is no intersection possible between 2 rectangles
        return this.getXPos() + getHitBox()[HITBOX_LEFT] < gameObject.getXPos() + gameObject.getHitBox()[HITBOX_RIGHT]
                && this.getXPos() + getHitBox()[HITBOX_RIGHT] > gameObject.getXPos() + gameObject.getHitBox()[HITBOX_LEFT]
                && this.getYPos() + getHitBox()[HITBOX_TOP] < gameObject.getYPos() + gameObject.getHitBox()[HITBOX_BOTTOM]
                && this.getYPos() + getHitBox()[HITBOX_BOTTOM] > gameObject.getYPos() + gameObject.getHitBox()[HITBOX_TOP];

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(double delta) {

    }
}
