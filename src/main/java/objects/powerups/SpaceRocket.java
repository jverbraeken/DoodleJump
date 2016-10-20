package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Propeller powerup.
 */
/* package */ final class SpaceRocket extends AJet {

    /**
     * The boost the SpaceRocket gives.
     */
    private static final double MAX_BOOST = -20d;

    /**
     * Y offset for drawing the SpaceRocket when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = -26;

    /**
     * SpaceRocket constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the SpaceRocket.
     * @param y - The Y location for the SpaceRocket.
     */
    /* package */ SpaceRocket(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, MAX_BOOST, sL.getSpriteFactory().getSpaceRocketSprite(), sL.getSpriteFactory().getSpaceRocketActiveSprites(), Propeller.class);
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

    }



}
