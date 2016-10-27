package objects.powerups;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

/**
 * /**
 * This class describes the abstract functionality of ATrampoline objects.
 */
public abstract class ATrampoline extends AJumpablePowerup {

    /**
     * The constructor of the ATrampoline object.
     * @param sL           The locator providing services to the powerup
     * @param point        The coordinates of the powerup
     * @param boost        The value of the boost of the powerup
     * @param sprites      The sprites, must be 2.
     * @param powerup      The class of the powerup
     */
    public ATrampoline(final IServiceLocator sL, final Point point, final double boost, final ISprite[] sprites, final Class<?> powerup) {
        super(sL, point, boost, sprites, powerup);
    }


    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (doodle.getVerticalSpeed() > 0 && doodle.getYPos() + doodle.getHitBox()[AGameObject.HITBOX_BOTTOM] < this.getYPos() + this.getHitBox()[AGameObject.HITBOX_BOTTOM]) {
            getLogger().info("Doodle collided with a Trampoline");
            doodle.collide(this);
        }
    }
}
