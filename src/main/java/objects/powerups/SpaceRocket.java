package objects.powerups;

import system.IServiceLocator;

import java.awt.Point;

/**
 * This class describes the behaviour of the SpaceRocket powerup.
 */
/* package */ final class SpaceRocket extends AJetpack {

    /**
     * The maximum time the space rocket is active.
     */
    private static final int MAX_TIME = 225;

    /**
     * Y offset for drawing the SpaceRocket when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = -70;


    /**
     * SpaceRocket constructor.
     *
     * @param sL - The Game's service locator
     * @param x - The X location for the space rocket
     * @param y - The Y location for the space rocket
     */
    /* package */ SpaceRocket(final IServiceLocator sL, final Point point) {
        super(sL, point, MAX_TIME, sL.getSpriteFactory().getPowerupSprite(Powerups.jetpack, 3), sL.getSpriteFactory().getSpaceRocketActiveSprites(), SpaceRocket.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition() {
        if (!this.getOwner().equals(null)) {
            this.setXPos((int) this.getOwner().getXPos() + ((this.getOwner().getSprite().getWidth() - this.getSprite().getWidth()) / 2));
            this.setYPos((int) this.getOwner().getYPos() - OWNED_Y_OFFSET);
        }
    }

}
