package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Propeller powerup.
 */
/* package */ final class Propeller extends APowerup implements IPassive, IPowerup {

    /**
     * The boost the Propeller gives.
     */
    private static final double BOOST = -15;
    /**
     * Y offset for drawing the Propeller when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = -10;

    /**
     * The Doodle that owns this Propeller.
     */
    private IDoodle owner;

    /**
     * Propeller constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the Propeller.
     * @param y - The Y location for the Propeller.
     */
    /* package */ Propeller(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getPropellerSprite(), Jetpack.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        return BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PassiveType getType() {
        return PassiveType.constant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (this.owner == null) {
            this.owner = doodle;
            doodle.setPassive(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        if (this.owner == null) {
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
        } else {
            int xPos = (int) this.owner.getXPos() + (this.getSprite().getWidth() / 2);
            int yPos = (int) this.owner.getYPos() + (this.getSprite().getHeight() / 2) + OWNED_Y_OFFSET;
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), xPos, yPos);
        }
    }

}
