package objects.powerups;

import objects.IJumpable;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Point;

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
     * @param point        The coordinates of the powerup
     * @param boost         The value of the boost of the powerup
     * @param sprites      The sprites, must be size 2.
     * @param powerup       The class of the powerup
     */
    /* package */ AJumpablePowerup(final IServiceLocator sL, final Point point, final double boost, final ISprite[] sprites, final Class<?> powerup) {
        super(sL, point, sprites[0], powerup);
        assert (sprites.length == 2);
        this.usedSprite = sprites[1];
        this.defaultSprite = sprites[0];
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
     * @param retractSpeed The speed with which the {@link AJumpablePowerup powerup} rectracts after it is used.
     */
    protected final void executeDefaultAnimation(final int retractSpeed) {
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
