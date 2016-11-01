package objects.powerups;

import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import system.IServiceLocator;

import java.awt.*;

/**
 * This class describes the behaviour of the SizeUp powerup. Increasing the size of the Doodle when picked up.
 */
/* package */ final class SizeUp extends APowerup implements IDisappearingPowerup {

    /**
     * Multiplier to hide the SizeUp when picked up.
     */
    private static final int HIDE_MULTIPLIER = -2;
    /**
     * The scale increase provided by the SizeUp powerup.
     */
    private final double scaleIncrease;

    /**
     * SizeUp constructor.
     *
     * @param sL    - The Games service locator.
     * @param point - The location for the SizeUp.
     */
    /* package */ SizeUp(final IServiceLocator sL, final Point point, final int level) {
        super(sL, point, Powerups.sizeUp, level);
        this.scaleIncrease = Powerups.sizeUp.getScale(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        this.getLogger().info("Doodle collided with a SizeUp");
        doodle.increaseSpriteScalar(this.scaleIncrease);

        // Hide the powerup so it will be deleted automatically
        this.setXPos(this.getSprite().getWidth() * SizeUp.HIDE_MULTIPLIER);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setPositionOnPlatform(final IPlatform platform) {
        super.setPositionOnPlatformRandom(platform);
    }
}
