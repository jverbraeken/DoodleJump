package objects.powerups;

import objects.IJumpable;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

/**
 * Created by Michael on 10/20/2016.
 */
public abstract class AJumpablePowerup extends APowerup implements IJumpable {

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
     * The constructor of the AJumpablePowerUp object.
     * @param sL           The locator providing services to the powerup
     * @param x            The X-coordinate of the powerup
     * @param y            The Y-coordinate of the powerup
     * @param boost        The value of the boost of the powerup
     * @param defaultSprite The sprite when the object has not collided with a doodle
     * @param usedSprite    The sprite when the object has collided with a doodle
     * @param powerup      The class of the powerup
     */
    public AJumpablePowerup(final IServiceLocator sL, final Point point, final double boost, final ISprite defaultSprite, final ISprite usedSprite, final Class<?> powerup) {
        super(sL, point, defaultSprite, powerup);
        this.usedSprite = usedSprite;
        this.defaultSprite = defaultSprite;
        this.boost = boost;
    }

    /**
     * Plays the sound of the this object.
     */
    abstract void playSound();

    /**
     * Updates the sprite that should be drawn in the scene.
     */
    abstract void animate();

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

}
