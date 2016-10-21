package objects.powerups;

import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */



/* package */ final class Jetpack extends APowerup implements IEquipmentPowerup {


    /**
     * The boost the Jetpack gives.
     */
    private static final int MAX_TIME = 175;

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
        super(sL, x, y, MAX_TIME, sL.getSpriteFactory().getJetpackSprite(), sL.getSpriteFactory().getJetpackActiveSprites(), Jetpack.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (this.getOwner() == null) {
            getLogger().info("Doodle collided with a Jetpack");
            this.setOwner(doodle);
            doodle.setPowerup(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition() {
        MovementBehavior.Directions facing = this.getOwner().getFacing();
        if (facing == MovementBehavior.Directions.Left) {
            this.setXPos((int) this.getOwner().getXPos() + this.getOwner().getHitBox()[HITBOX_RIGHT]);
        } else {
            this.setXPos((int) this.getOwner().getXPos());
        }
        this.setYPos((int) this.getOwner().getYPos() + OWNED_Y_OFFSET);
    }

}
