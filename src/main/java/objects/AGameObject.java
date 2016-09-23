package objects;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.Game;
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
    private double xPos;
    private double yPos;

    /**
     * Creates a new game object and determines its hitbox by using the sprites dimensions automatically.
     * @param x The X-coordinate of the game object
     * @param y The Y-coordinate of the game object
     * @param sprite The sprite of the game object. Can be {null} when the object is a {@link objects.blocks.IBlock block}
     */
    public AGameObject(final IServiceLocator serviceLocator, int x, int y, ISprite sprite) {
        AGameObject.serviceLocator = serviceLocator;
        setXPos(x);
        setYPos(y);
        if (sprite == null) {
            //TODO This is not so awesome
            setHitBox(x, y, serviceLocator.getConstants().getGameWidth(), Integer.MAX_VALUE);
        } else {
            setHitBox(0, 0, sprite.getWidth(), sprite.getHeight());
            setSprite(sprite);
        }
    }

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
    public void setHitBox(int left, int top, int right, int bottom) {
        this.hitBox[HITBOX_LEFT] = left;
        this.hitBox[HITBOX_TOP] = top;
        this.hitBox[HITBOX_RIGHT] = right;
        this.hitBox[HITBOX_BOTTOM] = bottom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSprite() {
        return this.sprite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSprite(ISprite sprite) {
        this.sprite = sprite;
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
