package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ final class SpaceRocket extends AJetpack {

    /**
     * The maximum time the Jetpack is active.
     */
    private static final int MAX_TIMER = 225;

    /**
     * Y offset for drawing the Jetpack when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = 70;

    /**
     * Jetpack constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the Jetpack.
     * @param y - The Y location for the Jetpack.
     */
    /* package */ SpaceRocket(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, MAX_TIMER, sL.getSpriteFactory().getRocketSprite(), sL.getSpriteFactory().getRocketActiveSprites(), SpaceRocket.class);
    }

    public void setPosition(IDoodle owner) {
        if (!owner.equals(null)) {
            this.setXPos((int) owner.getXPos() + ((owner.getSprite().getWidth() - this.getSprite().getWidth()) / 2));
            this.setYPos((int) owner.getYPos() - OWNED_Y_OFFSET);
        }
    }

}
