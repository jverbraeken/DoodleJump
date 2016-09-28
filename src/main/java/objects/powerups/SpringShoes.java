package objects.powerups;

import objects.IJumpable;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the spring powerup.
 */
/* package */ class SpringShoes extends APowerup implements IPowerup, IJumpable {

    /**
     * temporary attribute
     */
    private boolean draw = true;

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
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        doodle.setSpringShoes(true);
        this.draw = false;
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        if (this.draw) {
            sL.getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
        }
    }

}
