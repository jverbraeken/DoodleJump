package objects.powerups;

import logging.ILogger;
import objects.blocks.platform.IPlatform;
import objects.doodles.doodle_behavior.MovementBehavior;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ final class Jetpack extends AEquipmentPowerup {

    /**
     * Y offset for drawing the Jetpack when on Doodle.
     */
    private final int ownedYOffset;
    /**
     * The level of the jetpack.
     */
    private final int level;
    /**
     * The logger.
     */
    private final ILogger logger;

    /**
     * Jetpack constructor.
     *
     * @param serviceLocator The service locator
     * @param point          The location for the powerup
     * @param level          The level of the powerup
     */
    /* package */ Jetpack(final IServiceLocator serviceLocator, final Point point, final int level) {
        super(serviceLocator, point, Powerups.jetpack, level);
        this.ownedYOffset = Powerups.jetpack.getOwnedYOffset(level);
        this.level = level;
        this.logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition() {
        if (level == 1 || level == 2) {
            MovementBehavior.Directions facing = getOwner().getFacing();
            if (facing.equals(MovementBehavior.Directions.Left)) {
                this.setXPos((int) getOwner().getXPos() + getOwner().getHitBox()[HITBOX_RIGHT]);
            } else {
                this.setXPos((int) getOwner().getXPos());
            }
        } else if (level == 3) {
            final double halfway = ((double) this.getOwner().getSprite().getWidth() - (double) this.getSprite().getWidth()) / 2d;
            this.setXPos(halfway + this.getOwner().getXPos());
        } else {
            final String error = "Trying to set the position of the jetpack based on the unknown level: " + level;
            logger.error(error);
            throw new RuntimeException(error);
        }
        this.setYPos((int) getOwner().getYPos() + ownedYOffset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPositionOnPlatform(final IPlatform platform) {
        super.setPositionOnPlatformRandom(platform);
    }

}
