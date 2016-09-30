package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the SizeUp powerup.
 */
/* package */ class SizeUp extends APowerup implements IPowerup {

    /**
     * The scale increase provided by the SizeUp powerup.
     */
    private static final double SCALE_INCREASE = 0.2d;

    /**
     * SizeUp constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the SizeUp.
     * @param y - The Y location for the SizeUp.
     */
    /* package */ SizeUp(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getSizeUpSprite(), Jetpack.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
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
