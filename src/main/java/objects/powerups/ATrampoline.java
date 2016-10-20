package objects.powerups;

import objects.IJumpable;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * /**
 * This class describes the abstract functionality of ATrampoline objects.
 */
public abstract class ATrampoline extends APowerup implements IJumpable {

    /**
     * The BOOST value for the ATrampoline.
     */
    private double boost;

    /**
     * The default sprite for the ATrampoline.
     */
    private static ISprite defaultSprite;

    /**
     * The used sprite for the ATrampoline.
     */
    private ISprite usedSprite;

    /**
     * The constructor of the ATrampoline object.
     * @param sL           The locator providing services to the powerup
     * @param x            The X-coordinate of the powerup
     * @param y            The Y-coordinate of the powerup
     * @param boost        The value of the boost of the powerup
     * @param defaultSprite The sprite when the object has not collided with a doodle
     * @param usedSprite    The sprite when the object has collided with a doodle
     * @param powerup      The class of the powerup
     */
    public ATrampoline(final IServiceLocator sL, final int x, final int y, final double boost, final ISprite defaultSprite, final ISprite usedSprite, final Class<?> powerup) {
        super(sL, x, y, defaultSprite, powerup);
        this.usedSprite = usedSprite;
        this.defaultSprite = defaultSprite;
        this.boost = boost;
    }

    /**
     * Plays the sound of the ATrampoline object.
     */
    abstract void playSound();

    /**
     * Returns the usedSprite when a doodle collides with this object.
     * @return a ISprite object.
     */
    public final ISprite getUsedSprite() {
        return this.usedSprite;
    }

    /**
     * Returns the default sprite.
     * @return a ISprite object.
     */
    public final ISprite getDefaultSprite() {
        return this.defaultSprite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getBoost() {
        this.animate();
        this.playSound();

        return this.boost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        doodle.collide(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Updates the sprite that should be drawn in the scene.
     */
    public void animate() {
        int oldHeight = getSprite().getHeight();
        ISprite newSprite = usedSprite;
        setSprite(newSprite);
        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
    }

}
