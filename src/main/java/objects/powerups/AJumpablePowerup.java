package objects.powerups;

import objects.IJumpable;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A powerup on which can be jumped.
 */
public abstract class AJumpablePowerup extends APowerup implements IJumpable {

    /**
     * The default sprite for the jumpable powerup.
     */
    private ISprite defaultSprite;
    /**
     * The BOOST value for the jumpable powerup.
     */
    private double boost;
    /**
     * The used sprite for the jumpable powerup.
     */
    private ISprite usedSprite;

    /**
     * The constructor of the AJumpablePowerUp object.
     *
     * @param sL            The locator providing services to the powerup
     * @param x             The X-coordinate of the powerup
     * @param y             The Y-coordinate of the powerup
     * @param boost         The value of the boost of the powerup
     * @param defaultSprite The sprite when the object has not collided with a doodle
     * @param usedSprite    The sprite when the object has collided with a doodle
     * @param powerup       The class of the powerup
     */
    /* package */ AJumpablePowerup(final IServiceLocator sL, final int x, final int y, final double boost, final ISprite defaultSprite, final ISprite usedSprite, final Class<?> powerup) {
        super(sL, x, y, defaultSprite, powerup);
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
     *
     * @return a ISprite object.
     */
    public final ISprite getUsedSprite() {
        return this.usedSprite;
    }

    /**
     * Returns the default sprite.
     *
     * @return a ISprite object.
     */
    public final ISprite getDefaultSprite() {
        return this.defaultSprite;
    }

    /**
     * Executes the default animation: a change between the default- and used-sprite after the player hit the powerup.
     *
     * @param powerup      The powerup that requested the animation
     * @param retractSpeed The speed with which the {@link AJumpablePowerup powerup} rectracts after it is used.
     */
    protected final void executeDefaultAnimation(final AJumpablePowerup powerup, final int retractSpeed) {
        int oldHeight = getSprite().getHeight();
        int newHeight = this.getUsedSprite().getHeight();
        this.addYPos(oldHeight - newHeight);
        this.setSprite(this.getUsedSprite());

        AJumpablePowerup self = this;
        new Timer().schedule(new TimerTask() {
            public void run() {
                self.addYPos(newHeight - oldHeight);
                self.setSprite(self.getDefaultSprite());
            }
        }, retractSpeed);
    }

}
