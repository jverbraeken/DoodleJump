package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

import java.awt.*;

/**
 * This class describes the behaviour of the SizeDown powerup. Decreasing the size of the Doodle when picked up.
 */
/* package */ final class SizeDown extends APowerup implements IDisappearingPowerup {

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
     * @param point - The location for the SizeUp.
     */
    /* package */ SizeDown(final IServiceLocator sL, final Point point) {
        super(sL, point, sL.getSpriteFactory().getPowerupSprite(Powerups.sizeDown, 1), SizeDown.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        SizeDown.getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
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
