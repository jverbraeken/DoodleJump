package objects.powerups;

import math.ICalc;
import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This class describes the abstract functionality of powerups.
 */
public abstract class APowerup extends AGameObject implements IPowerup {

    /**
     * Creates a new powerup and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param sL      The locator providing services to the powerup
     * @param point   The coordinates of the powerup
     * @param sprite  The sprite of the powerup
     * @param powerup The class of the powerup
     */
    public APowerup(final IServiceLocator sL, final Point point, final ISprite sprite, final Class<?> powerup) {
        super(sL, point, sprite, powerup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform(final PowerupOccasion occasion) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), new Point((int) this.getXPos(), (int) this.getYPos()));
    }

    /**
     * Called when the powerup is finished (e.g. flying).
     */
    public void endPowerup() {
        // Most powerups do not need any code here
    }


    /**
     * Set the x and y position of the powerup that's spawning on a platform.
     *
     * @param platform the platform object where the powerup is going to spawn.
     */
    public abstract void setPositionOnPlatform(final IPlatform platform);

    /**
     * Sets the position of the powerup at a random position on the platform.
     *
     * @param platform The platform at which the powerup must be placed
     */
    protected final void setPositionOnPlatformRandom(final IPlatform platform) {
        ICalc calc = AGameObject.getServiceLocator().getCalc();

        double[] hitbox = platform.getHitBox();
        final int platformWidth = (int) hitbox[AGameObject.HITBOX_RIGHT];
        int powerupXPos = (int) (calc.getRandomDouble(platformWidth));
        double[] powHitbox = this.getHitBox();
        final int powerupHeight = (int) powHitbox[AGameObject.HITBOX_BOTTOM];
        setXPos(setXPosOfPowerup(powerupXPos, (int) platform.getXPos(), platformWidth));
        setYPos((int) platform.getYPos() - powerupHeight);
    }

    /**
     * Sets the X position of the powerup depending on the type of the powerup.
     *
     * @param powerupXPos   The X position on the platform that has been randomly chosen.
     * @param xPosPlatform  The X position of the platform.
     * @param platformWidth The width of the platform.
     * @return integer of the X position of the powerup.
     */
    protected final int setXPosOfPowerup(final int powerupXPos, final int xPosPlatform, final int platformWidth) {
        double[] powHitbox = this.getHitBox();
        final int powerupWidth = (int) powHitbox[AGameObject.HITBOX_RIGHT];

        int xPos = xPosPlatform + powerupXPos;
        if (xPos > xPosPlatform + platformWidth - powerupWidth) {
            xPos = xPos - powerupWidth;
        }
        return xPos;
    }

}
