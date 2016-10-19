package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the SizeUp powerup. Increasing the size of the Doodle when picked up.
 */
/* package */ final class SizeUp extends APowerup implements IDisappearingPowerup {

    /**
     * The scale increase provided by the SizeUp powerup.
     */
    private static final double SCALE_INCREASE = 0.4d;

    /**
     * SizeUp constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the SizeUp.
     * @param y - The Y location for the SizeUp.
     */
    /* package */ SizeUp(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getSizeUpSprite(), SizeUp.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        getLogger().info("Doodle collided with a SizeUp");
        doodle.increaseSpriteScalar(SCALE_INCREASE);
        this.setXPos(this.getSprite().getWidth() * -2); // Hide the powerup so it will be deleted automatically
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

}
