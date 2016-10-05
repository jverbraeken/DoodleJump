package objects.powerups;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ class Jetpack extends APowerup implements IPassive, IPowerup {

    /**
     * The acceleration provided by the Jetpack.
     */
    private static final double ACCELERATION = -2d;
    /**
     * The boost the Jetpack gives.
     */
    private static final double MAX_BOOST = -20d;
    /**
     * The maximum time the Jetpack is active.
     */
    private static final int MAX_TIMER = 100;

    /**
     * The sprites for an active rocket.
     */
    private static ISprite[] spritePack;
    /**
     * The Doodle that owns this Jetpack.
     */
    private IDoodle owner;
    /**
     * The active timer for the Jetpack.
     */
    private int timer = 0;
    /**
     * The active speed provided by the Jetpack.
     */
    private double speed = 0;

    /**
     * Jetpack constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the Jetpack.
     * @param y - The Y location for the Jetpack.
     */
    /* package */ Jetpack(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getJetpackSprite(), Jetpack.class);

        Jetpack.spritePack = sL.getSpriteFactory().getJetpackActiveSprites();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        return this.speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        timer += 1;

        if (timer == MAX_TIMER) {
            this.owner.removePassive(this);
            this.owner = null;
        } else if (this.speed > MAX_BOOST) {
            this.speed += ACCELERATION;
        }

        this.setSprite(Jetpack.spritePack[timer % 9]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PassiveType getType() {
        return PassiveType.constant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (this.owner == null) {
            this.owner = doodle;
            doodle.setPassive(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        if (this.owner == null) {
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
        } else {
            int xPos = (int) this.owner.getXPos();
            int yPos = (int) this.owner.getYPos() + (this.getSprite().getHeight() / 2);
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), xPos, yPos);
        }
    }

}
