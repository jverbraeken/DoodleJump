package objects.powerups;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the rocket launcher powerup.
 */
/* package */ public final class RocketLauncher extends ATrampoline {

    /**
     * RocketLauncher constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the rocket launcher.
     * @param y - The Y location for the rocket launcher.
     */
    @SuppressWarnings("magicnumber")
    /* package */ RocketLauncher(final IServiceLocator sL, final int x, final int y) {
        super(sL,
                x,
                y - sL.getSpriteFactory().getRocketLauncherSprite().getHeight(),
                -100,
                sL.getSpriteFactory().getRocketLauncherSprite(),
                sL.getSpriteFactory().getRocketLauncherUsedSprite(),
                Trampoline.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playSound() { }

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
