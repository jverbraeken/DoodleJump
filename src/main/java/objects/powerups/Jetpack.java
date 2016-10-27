package objects.powerups;

import logging.ILogger;
import objects.blocks.platform.IPlatform;
import objects.doodles.doodle_behavior.MovementBehavior;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ final class Jetpack extends AFlyablePowerup {

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
     * @param serviceLocator The Game's service locator.
     * @param x              The X location for the Jetpack.
     * @param y              The Y location for the Jetpack.
     * @param level          The level of the Jetpack
     * @param activeSprites  The animation used when the Jetpack is flying
     * @param maxTime        The time in frames the Jetpack can fly
     * @param ownedYOffset   The Y-offset for drawing the Jetpack when the Doodle is flying with it
     */
    /* package */ Jetpack(final IServiceLocator serviceLocator, final int x, final int y, final int level, final ISprite[] activeSprites, final int maxTime, final int ownedYOffset) {
        super(serviceLocator, x, y, maxTime, serviceLocator.getSpriteFactory().getPowerupSprite(Powerups.jetpack, level), activeSprites, Jetpack.class);
        this.ownedYOffset = ownedYOffset;
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
            this.setXPos((((double) this.getOwner().getSprite().getWidth() - (double) this.getSprite().getWidth()) / 2d) + this.getOwner().getXPos());
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
