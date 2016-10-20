package objects.powerups;

import math.ICalc;
import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the abstract functionality of powerups.
 */
public abstract class APowerup extends AGameObject implements IPowerup {

    /**
     * Creates a new powerup and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param sL           The locator providing services to the powerup
     * @param x            The X-coordinate of the powerup
     * @param y            The Y-coordinate of the powerup
     * @param sprite       The sprite of the powerup
     * @param powerup      The class of the powerup
     */
    public APowerup(final IServiceLocator sL, final int x, final int y, final ISprite sprite, final Class<?> powerup) {
        super(sL, x, y, sprite, powerup);
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
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Set the x and y position of the powerup that's spawning on a platform.
     * @param powerup a IGameObject that's going to be spawning.
     * @param platform the platform object where the powerup is going to spawn.
     */
    public void setPositionOnPlatform(final IGameObject powerup, final IPlatform platform) {
        ICalc calc = AGameObject.getServiceLocator().getCalc();

        double[] hitbox = platform.getHitBox();
        final int platformWidth = (int) hitbox[AGameObject.HITBOX_RIGHT];
        final int platformHeight = (int) hitbox[AGameObject.HITBOX_BOTTOM];
        int powerupXPos = (int) (calc.getRandomDouble(platformWidth));
        double[] powHitbox = powerup.getHitBox();
        final int powerupHeight = (int) powHitbox[AGameObject.HITBOX_BOTTOM];
        powerup.setXPos(setXPosOfPowerup(powerup, powerupXPos, (int) platform.getXPos(), platformWidth));
        powerup.setYPos((int) platform.getYPos() - platformHeight / 2 - powerupHeight / 2);
    }


    /**
     * Sets the X position of the powerup depending on the type of the powerup.
     * @param powerup The powerup object that's going to be set.
     * @param powerupXPos The X position on the platform that has been randomly chosen.
     * @param xPosPlatform The X position of the platform.
     * @param platformWidth The width of the platform.
     * @return integer of the X position of the powerup.
     */
    public int setXPosOfPowerup(final IGameObject powerup, final int powerupXPos, final int xPosPlatform, final int platformWidth) {
        double[] powHitbox = powerup.getHitBox();
        final int powerupWidth = (int) powHitbox[AGameObject.HITBOX_RIGHT];

        int xPos = xPosPlatform + powerupXPos;
        if (xPos > xPosPlatform + platformWidth - powerupWidth) {
            xPos = xPos - powerupWidth;
        }
        return xPos;
    }

}
