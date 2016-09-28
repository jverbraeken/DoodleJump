package objects.powerups;

import objects.IJumpable;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the spring powerup.
 */
/* package */ class SpringShoes extends APowerup implements IPassive, IPowerup {

    /**
     * The doodle that owns these shoes.
     */
    private IDoodle owner;

    /**
     * Trampoline constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the trampoline.
     * @param y - The Y location for the trampoline.
     */
    /* package */ SpringShoes(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getSpringShoesSprite());
    }

    /** {@inheritDoc} */
    @Override
    public double getBoost() {
        return 2;
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        if (this.owner == null) {
            this.owner = doodle;
            doodle.setPassive(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        if (this.owner == null) {
            sL.getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
        } else {
            int xPos = (int) owner.getXPos() + (owner.getSprite().getWidth() / 2) - (this.getSprite().getWidth() / 2);
            int yPos = (int) owner.getYPos() + owner.getSprite().getHeight();
            sL.getRenderer().drawSprite(this.getSprite(), xPos, yPos);
        }
    }

}
