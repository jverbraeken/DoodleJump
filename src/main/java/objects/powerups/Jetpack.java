package objects.powerups;

import objects.doodles.DoodleBehavior.MovementBehavior;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behaviour of the Jetpack powerup.
 */
/* package */ final class Jetpack extends APowerup implements IEquipmentPowerup {

    /**
     * The acceleration provided by the Jetpack.
     */
    private static final double ACCELERATION = -2d;
    /**
     * The boost for the Jetpack when it is being dropped.
     */
    private static final double INITIAL_DROP_SPEED = -25d;
    /**
     * The boost the Jetpack gives.
     */
    private static final double MAX_BOOST = -25d;
    /**
     * The horizontal speed for a Jetpack.
     */
    private static final double HORIZONTAL_SPEED = 1.2d;
    /**
     * Percentage for the initial phase of the Jetpack animation.
     */
    private static final double ANIMATION_PHASE_ONE = 0.15d;
    /**
     * Percentage for the second phase of the Jetpack animation.
     */
    private static final double ANIMATION_PHASE_TWO = 0.8d;
    /**
     * Percentage for the third phase of the Jetpack animation.
     */
    private static final double ANIMATION_PHASE_THREE = 1d;
    /**
     * The refresh rate for the active animation.
     */
    private static final int ANIMATION_REFRESH_RATE = 3;
    /**
     * The maximum time the Jetpack is active.
     */
    private static final int MAX_TIMER = 175;
    /**
     * Offset for the initial phase of the Jetpack animation.
     */
    private static final int ANIMATION_OFFSET_ONE = 0;
    /**
     * Offset for the second phase of the Jetpack animation.
     */
    private static final int ANIMATION_OFFSET_TWO = 3;
    /**
     * Offset for the third phase of the Jetpack animation.
     */
    private static final int ANIMATION_OFFSET_THREE = 6;
    /**
     * Y offset for drawing the Jetpack when on Doodle.
     */
    private static final int OWNED_Y_OFFSET = 35;

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
        super(sL, x, y, sL.getSpriteFactory().getPowerupSprite(Powerups.jetpack, 1), Jetpack.class);
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
        if (this.owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }

        if (occasion == PowerupOccasion.constant) {
            this.owner.setVerticalSpeed(this.vSpeed);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

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
            int offset = ANIMATION_OFFSET_ONE;
            if (percentage > ANIMATION_PHASE_ONE && percentage < ANIMATION_PHASE_TWO) {
                offset = ANIMATION_OFFSET_TWO;
            } else if (percentage < ANIMATION_PHASE_THREE) {
                offset = ANIMATION_OFFSET_THREE;
            }

            this.spriteIndex = offset + ((spriteIndex + 1) % ANIMATION_REFRESH_RATE);
            this.setSprite(Jetpack.spritePack[this.spriteIndex]);
        }

        if (this.owner != null) {
            MovementBehavior.Directions facing = this.owner.getFacing();
            if (facing == MovementBehavior.Directions.Left) {
                this.setXPos((int) this.owner.getXPos() + this.owner.getHitBox()[HITBOX_RIGHT]);
            } else {
                this.setXPos((int) this.owner.getXPos());
            }
            this.setYPos((int) this.owner.getYPos() + OWNED_Y_OFFSET);
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
        this.setSprite(spritePack[spritePack.length - 1]);
        this.vSpeed = INITIAL_DROP_SPEED;

        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
    }

}
