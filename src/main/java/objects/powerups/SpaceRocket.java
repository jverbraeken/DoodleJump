package objects.powerups;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import system.IServiceLocator;

import java.awt.*;

/**
 * This class describes the behaviour of the SpaceRocket powerup.
 */
/* package */ final class SpaceRocket extends AJetpack {

    /**
     * The maximum time the space rocket is active.
     */
    private static final int MAX_TIME = 225;

    /**
     * Y offset for drawing the SpaceRocket when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = -70;


    /**
     * SpaceRocket constructor.
     *
     * @param sL - The Game's service locator
     * @param point - The location of the space rocket
     */
    /* package */ SpaceRocket(final IServiceLocator sL, final Point point) {
        super(sL, point, MAX_TIME, sL.getSpriteFactory().getPowerupSprite(Powerups.jetpack, 3), sL.getSpriteFactory().getSpaceRocketActiveSprites(), SpaceRocket.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition() {
        if (this.getOwner() !=null) {
            this.setXPos((int) this.getOwner().getXPos() + ((this.getOwner().getSprite().getWidth() - this.getSprite().getWidth()) / 2));
            this.setYPos((int) this.getOwner().getYPos() + OWNED_Y_OFFSET);
        }
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
        final int powerupHeight = (int) powHitbox[AGameObject.HITBOX_BOTTOM];
        powerup.setXPos((int) platform.getXPos() + (platformWidth / 2) - (powerup.getSprite().getWidth() / 2));
        powerup.setYPos((int) platform.getYPos() - platformHeight / 2 - powerupHeight / 1.2);
    }

}
