package objects.powerups;

import objects.doodles.DoodleBehavior.MovementBehavior;
import system.IServiceLocator;
import java.awt.Point;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */



/* package */ final class Afterburner extends AJetpack {


    /**
     * The time limit of the afterburner.
     */
    private static final int MAX_TIME = 200;

    /**
     * Y offset for drawing the afterburner when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = 35;

    /**
     * Afterburner constructor.
     *
     * @param sL - The Game's service locator.
     * @param point - The location for the afterburner.
     */
    /* package */ Afterburner(final IServiceLocator sL, final Point point) {
        super(sL, point, MAX_TIME, sL.getSpriteFactory().getPowerupSprite(Powerups.jetpack, 2), sL.getSpriteFactory().getSpaceRocketActiveSprites(), Afterburner.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition() {
        MovementBehavior.Directions facing = this.getOwner().getFacing();
        if (facing.equals(MovementBehavior.Directions.Left)) {
            this.setXPos((int) this.getOwner().getXPos() + this.getOwner().getHitBox()[HITBOX_RIGHT]);
        } else {
            this.setXPos((int) this.getOwner().getXPos());
        }
        this.setYPos((int) this.getOwner().getYPos() + OWNED_Y_OFFSET);
    }

}

