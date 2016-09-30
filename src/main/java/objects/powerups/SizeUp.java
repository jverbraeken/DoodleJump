package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the SizeUp powerup.
 */
/* package */ class SizeUp extends APowerup implements IPowerup {

    /**
     * The Doodle that owns this Propeller.
     */
    private IDoodle owner;

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

    @Override
    public void collidesWith(IDoodle doodle) {
        // TODO: Make Doodle bigger
    }

    @Override
    public void render() {
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

}
