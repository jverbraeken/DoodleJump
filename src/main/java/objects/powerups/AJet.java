package objects.powerups;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Created by Michael on 10/20/2016.
 */
public abstract class AJet extends APowerup {

    /**
     * The acceleration provided by the Jetpack.
     */
    private static final double ACCELERATION = -2d;
    /**
     * The boost for the Jetpack when it is being dropped.
     */
    private static final double INITIAL_DROP_SPEED = -25d;
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
     * The sprites for an active rocket.
     */
    private ISprite[] spritePack;
    /**
     * The Doodle that owns this Jetpack.
     */
    private IDoodle owner;

    /**
     * The boost the Jetpack gives.
     */
    private double maxBoost;

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
    public AJet(final IServiceLocator sL,
                final int x,
                final int y,
                final double maxBoost,
                final ISprite sprite,
                final ISprite[] sprites,
                final Class<?> powerup) {
        super(sL, x, y, sprite, powerup);
        this.maxBoost = maxBoost;
        this.spritePack = sprites;
    }

    abstract void setPosition();

    /**
     * {@inheritDoc}
     */
    @Override
    public final void perform(final PowerupOccasion occasion) {
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
    public final void update(final double delta) {
        if (this.owner != null) {
            this.updateOwned();
        } else if (this.timer > 0) {
            this.updateFalling();
        }
    }


    /**
     * Update method for when the Jetpack is falling.
     */
    private final void updateFalling() {
        this.applyGravity();
        this.addXPos(HORIZONTAL_SPEED);
    }

    /**
     * Applies gravity to the Propeller when needed.
     */
    private final void applyGravity() {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        this.addYPos(this.vSpeed);
    }

    /**
     * Ends the powerup.
     */
    private final void endPowerup() {
        this.setSprite(spritePack[spritePack.length - 1]);
        this.vSpeed = INITIAL_DROP_SPEED;

        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
    }

    /**
     * Update method for when the Jetpack is owned.
     */
    private final void updateOwned() {
        timer++;

        if (timer >= MAX_TIMER) {
            this.endPowerup();
            return;
        } else if (this.vSpeed > maxBoost) {
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
            this.setSprite(this.spritePack[this.spriteIndex]);
        }

        if (this.owner != null) {
            setPosition();
        }
    }

    public final IDoodle getOwner() {
        return owner;
    }

    public final void setOwner(final IDoodle doodle) {
        this.owner = doodle;
    }

}
