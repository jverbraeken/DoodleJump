package objects.powerups;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ class Jetpack extends APowerup {

    /**
     * The acceleration provided by the Jetpack.
     */
    private static final double ACCELERATION = -2d;
    /**
     * The boost the Jetpack gives.
     */
    private static final double MAX_BOOST = -25d;
    /**
     * The maximum time the Jetpack is active.
     */
    private static final int MAX_TIMER = 175;

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
    public final void update(final double delta) {
        timer += 1;

        if (timer == MAX_TIMER) {
            this.owner.removePowerup(this);
            this.owner = null;
        } else if (this.speed > MAX_BOOST) {
            this.speed += ACCELERATION;
        }

        double x = (double) timer / (double) MAX_TIMER;
        int spriteIndex = 0;
        if (x < .15) {
            spriteIndex = (timer % 2) + 2;
        } else if (x < .85) {
            spriteIndex = (timer % 4) + 3;
        } else if (x < .95) {
            spriteIndex = (timer % 2) + 7;
        } else {
            spriteIndex = Jetpack.spritePack.length - 1;
        }

        this.setSprite(Jetpack.spritePack[spriteIndex]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform(final String occasion) {
        if (occasion.equals("constant")) {
            this.owner.setVerticalSpeed(this.speed);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (this.owner == null) {
            getLogger().info("Doodle collided with a Jetpack");
            this.owner = doodle;
            doodle.setPowerup(this);
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
