package objects.powerups;

import objects.doodles.DoodleBehavior.MovementBehavior;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ final class Jetpack extends AJetpack {

    /**
     * The maximum time the Jetpack is active.
     */
    private static final int MAX_TIME = 175;

    /**
     * Y offset for drawing the Jetpack when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = 35;

    /**
     * Jetpack constructor.
     *
     * @param sL - The Game's service locator.
     * @param point - The location for the Jetpack.
     */
    /* package */ Jetpack(final IServiceLocator sL, final Point point) {
        super(sL, point, MAX_TIME, sL.getSpriteFactory().getPowerupSprite(Powerups.jetpack, 1), sL.getSpriteFactory().getJetpackActiveSprites(), Jetpack.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override

    public void render() {
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos(), this.getAngle());
    }

    /**
     * Set the position of the jetpack with respect to the Doodle owning it.
     */
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

