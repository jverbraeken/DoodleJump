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
/* package */ abstract class AJumpablePowerup extends APowerup implements IJumpable {

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
     */
    /* package */ AJumpablePowerup(final IServiceLocator sL, final Point point, final Powerups type, final int level) {
        super(sL, point, type, level);
        this.boost = type.getBoost(level);
        this.defaultSprite = sL.getSpriteFactory().getPowerupSprite(type, level);
        this.usedSprite = sL.getSpriteFactory().getSprite(type.getUsedSprite(level));
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
     * Executes the default animation: a change between the default- and used-sprite after the player hit the powerup.
     *
     * @param retractSpeed The speed with which the {@link AJumpablePowerup powerup} rectracts after it is used.
     */
    protected final void executeDefaultAnimation(final int retractSpeed) {
        final int oldHeight = getSprite().getHeight();
        final int newHeight = this.usedSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
        this.setSprite(this.usedSprite);

        final AJumpablePowerup self = this;
        new Timer().schedule(new TimerTask() {
            public void run() {
                self.addYPos(newHeight - oldHeight);
                self.setSprite(self.defaultSprite);
            }
        }, retractSpeed);
    }

}
