package objects.powerups;

import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class describes the abstract functionality of ASprings.
 */
public abstract class ASpring extends AJumpablePowerup {

    /**
     * The speed with which the ASpring retracts after it is being used.
     */
    private int retractSpeed;
    /**
     * The constructor of the ASpring object.
     * @param sL           The locator providing services to the powerup
     * @param x            The X-coordinate of the powerup
     * @param y            The Y-coordinate of the powerup
     * @param boost        The value of the boost of the powerup
     * @param defaultSprite The sprite when the object has not collided with a doodle
     * @param usedSprite    The sprite when the object has collided with a doodle
     * @param powerup      The class of the powerup
     */
    public ASpring(final IServiceLocator sL,
                   final int x,
                   final int y,
                   final double boost,
                   final int retractSpeed,
                   final ISprite defaultSprite,
                   final ISprite usedSprite,
                   final Class<?> powerup) {
        super(sL, x, y, boost, defaultSprite, usedSprite, powerup);
        this.retractSpeed = retractSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate() {
        int oldHeight = getSprite().getHeight();
        int newHeight = this.getUsedSprite().getHeight();
        this.addYPos(oldHeight - newHeight);
        this.setSprite(this.getUsedSprite());

        ASpring self = this;
        new Timer().schedule(new TimerTask() {
            public void run() {
                self.addYPos(newHeight - oldHeight);
                self.setSprite(self.getDefaultSprite());
            }
        }, retractSpeed);
    }
}
