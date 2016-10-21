package objects.powerups;

import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ final class Jetpack extends AJetpack {

    /**
     * The maximum time the Jetpack is active.
     */
    private static final int MAX_TIMER = 175;
    /**
     * Y offset for drawing the Jetpack when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = 35;

    /**
     * Jetpack constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the Jetpack.
     * @param y - The Y location for the Jetpack.
     */
    /* package */ Jetpack(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, MAX_TIMER, sL.getSpriteFactory().getJetpackSprite(), sL.getSpriteFactory().getJetpackActiveSprites(), Jetpack.class);
    }

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
