package objects.powerups;

import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This class describes the behaviour of the SizeDown powerup. Decreasing the size of the Doodle when picked up.
 */
/* package */ final class SizeDown extends APowerup implements IDisappearingPowerup {

    /**
     * Multiplier to hide the SizeDown when picked up.
     */
    private static final int HIDE_MULTIPLIER = -2;
    /**
     * The scale increase provided by the SizeUp powerup.
     */
    private final double scaleIncrease;

    /**
     * SizeUp constructor.
     *
     * @param serviceLocator The service locator
     * @param point          The location for the powerup
     * @param level          The level of the powerup
     */
    /* package */ SizeDown(final IServiceLocator serviceLocator, final Point point, final int level) {
        super(serviceLocator, point, Powerups.sizeDown, level);
        this.scaleIncrease = Powerups.sizeDown.getScale(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        this.getLogger().info("Doodle collided with a SizeDown");
        doodle.increaseSpriteScalar(this.scaleIncrease);

        // Hide the powerup so it will be deleted automatically
        this.setXPos(this.getSprite().getWidth() * SizeDown.HIDE_MULTIPLIER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPositionOnPlatform(final IPlatform platform) {
        super.setPositionOnPlatformRandom(platform);
    }
}
