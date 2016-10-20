package objects.powerups;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * /**
 * This class describes the abstract functionality of ATrampoline objects.
 */
public abstract class ATrampoline extends AJumpablePowerup {

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
        super(sL, x, y, boost, defaultSprite, usedSprite, powerup);
    }


    /**
     * Updates the sprite that should be drawn in the scene.
     */
    public void animate() {
        int oldHeight = getSprite().getHeight();
        ISprite newSprite = getUsedSprite();
        setSprite(newSprite);
        int newHeight = newSprite.getHeight();
        this.addYPos(oldHeight - newHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPositionOnPlatform(final IGameObject powerup, final IPlatform platform) {
        double[] hitbox = platform.getHitBox();
        final int platformWidth = (int) hitbox[AGameObject.HITBOX_RIGHT];
        final int platformHeight = (int) hitbox[AGameObject.HITBOX_BOTTOM];
        double[] powHitbox = powerup.getHitBox();
        final int powerupWidth = (int) powHitbox[AGameObject.HITBOX_RIGHT];
        final int powerupHeight = (int) powHitbox[AGameObject.HITBOX_BOTTOM];
        powerup.setXPos((platform.getXPos() + (platformWidth / 2)) - (powerupWidth / 2));
        powerup.setYPos((int) platform.getYPos() - platformHeight / 2 - powerupHeight / 2);
    }
}
