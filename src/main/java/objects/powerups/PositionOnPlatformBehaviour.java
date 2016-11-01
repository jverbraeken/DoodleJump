package objects.powerups;

import math.ICalc;
import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.powerups.Powerup;

public final class PositionOnPlatformBehaviour {
    public final void execute(final Powerup powerup, final IPlatform platform) {
        ICalc calc = AGameObject.getServiceLocator().getCalc();

        double[] hitbox = platform.getHitBox();
        final int platformWidth = (int) hitbox[AGameObject.HITBOX_RIGHT];
        final int platformHeight = (int) hitbox[AGameObject.HITBOX_BOTTOM];
        int powerupXPos = (int) (calc.getRandomDouble(platformWidth));
        double[] powHitbox = powerup.getHitBox();
        final int powerupHeight = (int) powHitbox[AGameObject.HITBOX_BOTTOM];
        powerup.setXPos(powerup.setXPosOfPowerup(powerupXPos, (int) platform.getXPos(), platformWidth));
        powerup.setYPos((int) platform.getYPos() - powerupHeight);
    }
}
