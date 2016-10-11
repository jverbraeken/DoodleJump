package objects.powerups;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ final class Jetpack extends APowerup {

    /**
     * The acceleration provided by the Jetpack.
     */
    private static final double ACCELERATION = -2d;
    /**
     * The refresh rate for the active animation.
     */
    private static final int ANIMATION_REFRESH_RATE = 3;
    /**
     * The boost for the Jetpack when it is being dropped.
     */
    private static final double DROP_BOOST = 1.1d;
    /**
     * The boost the Jetpack gives.
     */
    private static final double MAX_BOOST = -25d;
    /**
     * The maximum time the Jetpack is active.
     */
    private static final int MAX_TIMER = 175;
    /**
     * The horizontal speed for a Jetpack.
     */
    private static final double HORIZONTAL_SPEED = 1.2d;

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
     * The vertical speed of the Jetpack.
     */
    private double vSpeed = 0d;
    /**
     * The index of the current sprite.
     */
    private int spriteIndex = 0;

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
    public void update(final double delta) {
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
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /**
     * Update method for when the Jetpack is owned.
     */
    private void updateOwned() {
        timer++;

        if (timer >= MAX_TIMER) {
            this.endPowerup();
            return;
        } else if (this.vSpeed > MAX_BOOST) {
            this.vSpeed += ACCELERATION;
        }

        if (this.timer % ANIMATION_REFRESH_RATE == 0) {
            double percentage = (double) timer / (double) MAX_TIMER;
            int offset = 0;
            if (percentage > 0.15 && percentage < 0.8) {
                offset = 3;
            } else if (percentage < 1) {
                offset = 6;
            }
            
            this.spriteIndex = (spriteIndex + 1) % 3;
            this.setSprite(Jetpack.spritePack[offset + this.spriteIndex]);
        }

        if (this.owner != null) {
            this.setXPos((int) this.owner.getXPos());
            this.setYPos((int) this.owner.getYPos() + (this.getSprite().getHeight() / 2 / 2));
        }
    }

    /**
     * Update method for when the Jetpack is falling.
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
        this.setSprite(getServiceLocator().getSpriteFactory().getJetpackActiveSprites()[8]);
        this.vSpeed *= DROP_BOOST;

        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
    }

}
