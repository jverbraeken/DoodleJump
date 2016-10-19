package objects.powerups;

import objects.AGameObject;
import objects.IJumpable;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Created by Michael on 10/19/2016.
 */
public abstract class ATrampoline extends AGameObject implements IJumpable {

    /**
     * The BOOST value for the ATrampoline.
     */
    private double BOOST;

    /**
     * The default sprite for the ATrampoline.
     */
    private static ISprite defaultSprite;

    /**
     * The used sprite for the ATrampoline.
     */
    private ISprite usedSprite;

    public ATrampoline(final IServiceLocator sL, final int x, final int y, final double boost, final ISprite defaultSprite, final ISprite usedSprite, final Class<?> powerup) {
        super(sL, x, y, defaultSprite, powerup);
        this.usedSprite = usedSprite;
        this.defaultSprite = defaultSprite;
        this.BOOST = boost;
    }

    /**
     * Plays the sound of the ATrampoline object.
     */
    abstract void playSound();

    /**
     * Returns the usedSprite when a doodle collides with this object.
     * @return a ISprite object.
     */
    final public ISprite getUsedSprite() { return this.usedSprite; }

    /**
     * Returns the default sprite.
     * @return a ISprite object.
     */
    final public ISprite getDefaultSprite() { return this.defaultSprite; }

    /**
     * {@inheritDoc}
     */
    @Override
    final public double getBoost() {
        this.animate();
        this.playSound();

        return this.BOOST;
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
