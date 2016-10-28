package objects.powerups;

import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This class describes the behaviour of the SizeUp powerup. Increasing the size of the Doodle when picked up.
 */
/* package */ final class SizeUp extends APowerup implements IDisappearingPowerup {

    /**
     * The scale increase provided by the SizeUp powerup.
     */
    private static final double SCALE_INCREASE = 0.4d;
    /**
     * Multiplier to hide the SizeUp when picked up.
     */
    private static final int HIDE_MULTIPLIER = -2;

    /**
     * SizeUp constructor.
     *
     * @param sL - The Games service locator.
     * @param point - The location for the SizeUp.
     */
    /* package */ SizeUp(final IServiceLocator sL, final Point point) {
        super(sL, point, sL.getSpriteFactory().getPowerupSprite(Powerups.sizeUp, 1), SizeUp.class);
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
        doodle.increaseSpriteScalar(SizeUp.SCALE_INCREASE);

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
