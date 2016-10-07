package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the SizeDown powerup. Decreasing the size of the Doodle when picked up.
 */
/* package */ class SizeDown extends APowerup {

    /**
     * The scale increase provided by the SizeUp powerup.
     */
    private static final double SCALE_INCREASE = -0.2d;

    /**
     * SizeUp constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the SizeUp.
     * @param y - The Y location for the SizeUp.
     */
    /* package */ SizeDown(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getSizeDownSprite(), SizeDown.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        getLogger().info("Doodle collided with a SizeDown");
        doodle.increaseSpriteScalar(SCALE_INCREASE);
        this.setXPos(this.getSprite().getWidth() * -1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

}
