package objects.powerups;

import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the SpringShoes powerup.
 */
/* package */ class SpringShoes extends APowerup implements IPassive, IPowerup {

    /**
     * The maximum amount of times SpringShoes can be used.
     */
    private static final int MAX_USES = 3;
    /**
     * The boost provided by the SpringShoes.
     */
    private static final double BOOST = 2d;

    /**
     * The Doodle that owns these SpringShoes.
     */
    private IDoodle owner;
    /**
     * The amount of times the SpringShoes are used.
     */
    private int uses = 0;

    /**
     * Jump boots constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the SpringShoes.
     * @param y - The Y location for the SpringShoes.
     */
    /* package */ SpringShoes(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getSpringShoesSprite(), SpringShoes.class);
    }

    /** {@inheritDoc} */
    @Override
    public double getBoost() {
        this.uses += 1;

        if (this.uses > MAX_USES - 1)  {
            this.owner.removePassive(this);
            this.owner = null;
        }

        return BOOST;
    }

    /** {@inheritDoc} */
    @Override
    public PassiveType getType() {
        return PassiveType.collision;
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (this.owner == null) {
            this.owner = doodle;
            doodle.setPassive(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        if (this.owner == null && this.uses < MAX_USES) {
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
        } else if (this.owner != null) {
            int xPos = (int) owner.getXPos() + (owner.getSprite().getWidth() / 2) - (this.getSprite().getWidth() / 2);
            int yPos = (int) owner.getYPos() + owner.getSprite().getHeight();
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), xPos, yPos);
        }
    }

}
