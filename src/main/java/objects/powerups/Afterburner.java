package objects.powerups;

import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the afterburner powerup.
 */
/* package */ final class Afterburner extends AJetpack {

    /**
     * The maximum time the afterburner is active.
     */
    private static final int MAX_TIMER = 200;
    /**
     * Y offset for drawing the afterburner when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = 35;

    /**
     * Afterburner constructor.
     *
     * @param sL - The Game's service locator.
     * @param x - The X location for the afterburner.
     * @param y - The Y location for the afterburner.
     */
    /* package */ Afterburner(final IServiceLocator sL, final int x, final int y) {
        // Because sprites for this object has't been found or created yet, this object will use jetpack sprites.
        super(sL, x, y, MAX_TIMER, sL.getSpriteFactory().getPowerupSprite(Powerups.jetpack, 1), sL.getSpriteFactory().getJetpackActiveSprites(), Jetpack.class);
    }

    /**
     *{@inheritDoc}
     */
    public void setPosition(IDoodle owner) {
        if (!owner.equals(null)) {
            MovementBehavior.Directions facing = owner.getFacing();
            if (facing.equals(MovementBehavior.Directions.Left)) {
                this.setXPos((int) owner.getXPos() + owner.getHitBox()[HITBOX_RIGHT]);
            } else {
                this.setXPos((int) owner.getXPos());
            }
            this.setYPos((int) owner.getYPos() + OWNED_Y_OFFSET);
        }
    }

}
