package objects.powerups;

import system.IServiceLocator;

/**
 * This class describes the behaviour of the SpaceRocket powerup.
 */
/* package */ final class SpaceRocket extends AJetpack {

    /**
     * The boost the SpaceRocket gives.
     */
    private static final int MAX_TIME = 225;

    /**
     * Y offset for drawing the SpaceRocket when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = -70;


    /**
     * SpaceRocket constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the SpaceRocket.
     * @param y - The Y location for the SpaceRocket.
     */
    /* package */ SpaceRocket(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, MAX_TIME, sL.getSpriteFactory().getPowerupSprite(Powerups.jetpack, 3), sL.getSpriteFactory().getSpaceRocketActiveSprites(), SpaceRocket.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition() {
        this.setXPos(((this.getOwner().getSprite().getWidth() - this.getSprite().getWidth()) / 2) + this.getOwner().getXPos());
        this.setYPos(this.getOwner().getYPos() + OWNED_Y_OFFSET);
    }



}
