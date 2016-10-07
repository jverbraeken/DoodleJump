package objects.powerups;

import objects.IGameObject;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Propeller powerup.
 */
/* package */ class Propeller extends APowerup {

    /**
     * The acceleration provided by the Propeller.
     */
    private static final double ACCELERATION = -1d;

    /**
     * The boost the Propeller gives.
     */
    private static final double MAX_BOOST = -15;
    /**
     * The maximum time the Propeller is active.
     */
    private static final int MAX_TIMER = 80;
    /**
     * Y offset for drawing the Propeller when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = -26;

    /**
     * The sprites for an active rocket.
     */
    private static ISprite[] spritePack;
    /**
     * The Doodle that owns this Propeller.
     */
    private IDoodle owner;
    /**
     * The active timer for the Propeller.
     */
    private int timer = 0;
    /**
     * The active speed provided by the Propeller.
     */
    private double speed = 0;

    /**
     * Propeller constructor.
     *
     * @param sL - The Games service locator.
     * @param x - The X location for the Propeller.
     * @param y - The Y location for the Propeller.
     */
    /* package */ Propeller(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getPropellerSprite(), Propeller.class);

        Propeller.spritePack = sL.getSpriteFactory().getPropellerActiveSprites();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update(final double delta) {
        timer += 1;

        if (timer == MAX_TIMER) {
            this.owner.removePowerup(this);
            this.owner = null;
        } else if (this.speed > MAX_BOOST) {
            this.speed += ACCELERATION;
        }

        this.setSprite(Propeller.spritePack[timer % 4]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void perform(final String occasion) {
        if (occasion.equals("constant")) {
            this.owner.setVerticalSpeed(this.speed);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        if (this.owner == null) {
            getLogger().info("Doodle collided with a Propeller");
            this.owner = doodle;
            doodle.setPowerup(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        if (this.owner == null) {
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
        } else {
            int xPos = (int) this.owner.getXPos() + (this.getSprite().getWidth() / 2);
            int yPos = (int) this.owner.getYPos() + (this.getSprite().getHeight() / 2) + OWNED_Y_OFFSET;
            getServiceLocator().getRenderer().drawSprite(this.getSprite(), xPos, yPos);
        }
    }

}
