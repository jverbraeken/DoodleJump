package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ class Jetpack extends APowerup implements IPassive, IPowerup {

    /**
     * The boost the Jetpack gives.
     */
    private static final int BOOST = -20;

    /**
     * The Doodle that owns this Jetpack.
     */
    private IDoodle owner;

    /**
     * Jetpack constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the Jetpack.
     * @param y - The Y location for the Jetpack.
     */
    /* package */ Jetpack(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getJetpackSprite(), Jetpack.class);
    }

    /** {@inheritDoc} */
    @Override
    public double getBoost() {
        return BOOST;
    }

    /** {@inheritDoc} */
    @Override
    public PassiveType getType() {
        return PassiveType.constant;
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (this.owner == null) {
            this.owner = doodle;
            doodle.setPassive(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        if (this.owner == null) {
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
        } else {
            int xPos = (int) this.owner.getXPos();
            int yPos = (int) this.owner.getYPos() + (this.getSprite().getHeight() / 2);
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), xPos, yPos);
        }
    }
}
