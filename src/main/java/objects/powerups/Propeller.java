package objects.powerups;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Propeller powerup.
 */
/* package */ final class Propeller extends APowerup {

    /**
     * The acceleration provided by the Propeller.
     */
    private static final double ACCELERATION = -1d;
    /**
     * The boost for the Propeller when it is being dropped.
     */
    private static final double DROP_BOOST = 1.1d;
    /**
     * The boost the Propeller gives.
     */
    private static final double MAX_BOOST = -20d;
    /**
     * The maximum time the Propeller is active.
     */
    private static final int MAX_TIMER = 150;
    /**
     * Y offset for drawing the Propeller when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = -26;
    /**
     * The refresh rate for the active animation.
     */
    private static final int ANIMATION_REFRESH_RATE = 3;
    /**
     * The horizontal speed for a Propeller.
     */
    private static final double HORIZONTAL_SPEED = 1.2d;

    /**
     * The sprites for an active Propeller.
     */
    private static ISprite[] spritePack;
    /**
     * The index of the current sprite.
     */
    private int spriteIndex = 0;
    /**
     * The Doodle that owns this Propeller.
     */
    private IDoodle owner;
    /**
     * The active timer for the Propeller.
     */
    private int timer = 0;
    /**
     * The vertical speed of the Propeller.
     */
    private double vSpeed = 0d;

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
        if (this.owner != null) {
            this.updateOwned();
        } else if (this.timer > 0) {
            this.updateFalling();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform(final PowerupOccasion occasion) {
        if (occasion == PowerupOccasion.constant) {
            this.owner.setVerticalSpeed(this.vSpeed);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (this.owner == null && this.timer == 0) {
            getLogger().info("Doodle collided with a Propeller");
            this.owner = doodle;
            doodle.setPowerup(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        if (this.owner != null) {
            this.setXPos((int) this.owner.getXPos() + (this.getSprite().getWidth() / 2));
            this.setYPos((int) this.owner.getYPos() + (this.getSprite().getHeight() / 2) + OWNED_Y_OFFSET);
        }

        getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Update method for when the Propeller is owned.
     */
    private void updateOwned() {
        this.timer++;

        if (this.timer >= MAX_TIMER) {
            this.endPowerup();
            return;
        } else if (this.vSpeed > MAX_BOOST) {
            this.vSpeed += ACCELERATION;
        }

        if (this.timer % ANIMATION_REFRESH_RATE == 0) {
            this.spriteIndex = (spriteIndex + 1) % Propeller.spritePack.length;
            this.setSprite(Propeller.spritePack[this.spriteIndex]);
        }
    }

    /**
     * Update method for when the Propeller is falling.
     */
    private void updateFalling() {
        this.applyGravity();
        this.addXPos(HORIZONTAL_SPEED);
    }

    /**
     * Applies gravity to the Propeller when needed.
     */
    private void applyGravity() {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        this.addYPos(this.vSpeed);
    }

    /**
     * Ends the powerup.
     */
    private void endPowerup() {
        this.setSprite(getServiceLocator().getSpriteFactory().getPropellerSprite());
        this.vSpeed *= DROP_BOOST;

        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
    }

}
