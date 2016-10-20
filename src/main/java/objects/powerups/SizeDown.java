package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the SizeDown powerup. Decreasing the size of the Doodle when picked up.
 */
/* package */ final class SizeDown extends APowerup {

    /**
     * The scale increase provided by the SizeUp powerup.
     */
    private static final double SCALE_INCREASE = -0.4d;
    /**
     * Multiplier to hide the SizeDown when picked up.
     */
    private static final int HIDE_MULTIPLIER = -2;

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
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        this.getLogger().info("Doodle collided with a SizeDown");
        doodle.increaseSpriteScalar(SizeDown.SCALE_INCREASE);

        // Hide the powerup so it will be deleted automatically
        this.setXPos(this.getSprite().getWidth() * SizeDown.HIDE_MULTIPLIER);
    }

}
