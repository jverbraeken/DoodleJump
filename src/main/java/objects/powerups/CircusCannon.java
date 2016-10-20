package objects.powerups;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import system.IServiceLocator;

/**
 * This class defines the behaviour of a Circus cannon object.
 */
public class CircusCannon extends ATrampoline {


    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    @SuppressWarnings("magicnumber")
    /* package */ CircusCannon(final IServiceLocator sL, final int x, final int y) {
        super(sL,
                x,
                y - sL.getSpriteFactory().getCannonSprite().getHeight(),
                -75,
                sL.getSpriteFactory().getCannonSprite(),
                sL.getSpriteFactory().getCannonUsedSprite(),
                Trampoline.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void playSound() { }

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

