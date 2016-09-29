package objects;

import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * The super class of all classes that represents objects in the game.
 */
public abstract class AGameObject implements IGameObject {

    /**
     * Constants to prevent incorrect element access of the {@link #hitBox} variable.
     */
    public static final transient int HITBOX_LEFT = 0,
            HITBOX_RIGHT = 1,
            HITBOX_TOP = 2,
            HITBOX_BOTTOM = 3;
    /**
     * The size of the hitbox array.
     */
    private static final int HITBOX_SIZE = 4;

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;
    /**
     * The logger for the class.
     */
    private final ILogger logger;
    /**
     * A lock to prevent concurrent modification of e.g. the service locator.
     */
    private static final Object lock = new Object();
    /**
     * The hitbox for the Game Object.
     */
    private final double[] hitBox = new double[HITBOX_SIZE];
    /**
     * The sprite for the Game Object.
     */
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
     *
     * @param sL            The serviceLocator.
     * @param x             The X-coordinate of the game object.
     * @param y             The Y-coordinate of the game object.
     * @param sprite        The sprite of the game object.
     * @param objectClass   The class of the object (e.g. Doodle.class)
     */
    public AGameObject(final IServiceLocator sL, final int x, final int y, final ISprite sprite, final Class<?> objectClass) {
        synchronized (lock) {
            AGameObject.serviceLocator = sL;
        }

        this.setXPos(x);
        this.setYPos(y);

        if (sprite == null) {
            this.setHitBox(x, y, sL.getConstants().getGameWidth(), Integer.MAX_VALUE);
        } else {
            this.setHitBox(0, 0, sprite.getWidth(), sprite.getHeight());
            this.setSprite(sprite);
        }
        logger = sL.getLoggerFactory().createLogger(objectClass);
    }

    /** {@inheritDoc} */
    @Override
    public final void addXPos(final double x) {
        double current = getXPos();
        this.setXPos(current + x);
    }

    /** {@inheritDoc} */
    @Override
    public final void addYPos(final double y) {
        double current = getYPos();
        setYPos(current + y);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean checkCollision(final IGameObject gameObject) {
        if (gameObject == null) {
            throw new IllegalArgumentException("gameObject cannot be null");
        }

        // If one of these boolean turns false there is no intersection possible between 2 rectangles
        return this.getXPos() + getHitBox()[HITBOX_LEFT] < gameObject.getXPos() + gameObject.getHitBox()[HITBOX_RIGHT]
                && this.getXPos() + getHitBox()[HITBOX_RIGHT] > gameObject.getXPos() + gameObject.getHitBox()[HITBOX_LEFT]
                && this.getYPos() + getHitBox()[HITBOX_TOP] < gameObject.getYPos() + gameObject.getHitBox()[HITBOX_BOTTOM]
                && this.getYPos() + getHitBox()[HITBOX_BOTTOM] > gameObject.getYPos() + gameObject.getHitBox()[HITBOX_TOP];
    }

    /** {@inheritDoc} */
    @Override
    public final double[] getHitBox() {
        return this.hitBox.clone();
    }

    /** {@inheritDoc} */
    @Override
    public final ISprite getSprite() {
        return this.sprite;
    }

    /** {@inheritDoc} */
    @Override
    public final double getXPos() {
        return this.xPos;
    }

    /** {@inheritDoc} */
    @Override
    public final double getYPos() {
        return this.yPos;
    }

    /** {@inheritDoc} */
    @Override
    public abstract void render();

    /** {@inheritDoc} */
    @Override
    public final void setHitBox(final int left, final int top, final int right, final int bottom) {
        this.hitBox[HITBOX_LEFT] = left;
        this.hitBox[HITBOX_TOP] = top;
        this.hitBox[HITBOX_RIGHT] = right;
        this.hitBox[HITBOX_BOTTOM] = bottom;
    }

    /** {@inheritDoc} */
    @Override
    public final void setSprite(final ISprite s) {
        this.sprite = s;
    }

    /** {@inheritDoc} */
    @Override
    public final void setXPos(final double x) {
        this.xPos = x;
    }

    /** {@inheritDoc} */
    @Override
    public final void setYPos(final double y) {
        this.yPos = y;
    }

    /**
     * @return The logger of the object.
     */
    public ILogger getLogger() {
        return logger;
    }

    /** {@inheritDoc} */
    @Override
    public void update(final double delta) { }

    /**
     * Get the serviceLocator.
     *
     * @return The serviceLocator.
     */
    protected static IServiceLocator getServiceLocator() {
        assert serviceLocator != null;
        return serviceLocator;
    }

}
