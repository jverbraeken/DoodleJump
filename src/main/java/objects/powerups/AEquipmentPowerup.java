package objects.powerups;

import objects.doodles.IDoodle;
import resources.IRes;
import resources.sprites.Animation;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

/**
 * Extended by classes that are powerups with which the Doodle can fly.
 */
public abstract class AEquipmentPowerup extends APowerup implements IEquipmentPowerup {

    /**
     * The boost the flyable powerup object provides.
     */
    private static final double MAX_BOOST = -25d;
    /**
     * The acceleration provided by the flyable powerup.
     */
    private static final double ACCELERATION = -2d;
    /**
     * The boost for the flyable powerup when it is being dropped.
     */
    private static final double INITIAL_DROP_SPEED = -25d;
    /**
     * The horizontal speed for a flyable powerup.
     */
    private static final double HORIZONTAL_SPEED = 1.2d;
    /**
     * Percentage for the initial phase of the flyable powerup animation.
     */
    private static final double ANIMATION_PHASE_ONE = 0.1d;
    /**
     * Percentage for the second phase of the flyable powerup animation.
     */
    private static final double ANIMATION_PHASE_TWO = 0.8d;
    /**
     * Percentage for the third phase of the flyable powerup animation.
     */
    private static final double ANIMATION_PHASE_THREE = 1d;
    /**
     * The refresh rate for the active animation.
     */
    private static final int ANIMATION_REFRESH_RATE = 3;

    /**
     * Offset for the initial phase of the flyable powerup animation.
     */
    private static final int ANIMATION_OFFSET_ONE = 0;
    /**
     * Offset for the second phase of the flyable powerup animation.
     */
    private static final int ANIMATION_OFFSET_TWO = 3;
    /**
     * Offset for the third phase of the flyable powerup animation.
     */
    private static final int ANIMATION_OFFSET_THREE = 6;

    /**
     * Default sprite of this flyable powerup object.
     */
    private ISprite defaultSprite;

    /**
     * The sprites for an active flyable powerup.
     */
    private Animation animation;
    /**
     * The Doodle that owns this flyable powerup.
     */
    private IDoodle owner;

    /**
     * The maximum time the flyable powerup is active.
     */
    private int timeLimit;
    /**
     * The active timer for the flyable powerup.
     */
    private int timer = 0;
    /**
     * The vertical speed of the flyable powerup.
     */
    private double vSpeed = 0d;
    /**
     * The index of the current sprite.
     */
    private int spriteIndex = 0;

    /**
     * Flyable powerup constructor.
     *
     * @param sL      The service locator
     * @param point   The location of the powerup
     * @param maxTime the time for which the powerup can power the doodle
     * @param sprite  The sprite used for the powerup placed on a platform
     * @param animation The animation used when the doodle equips the powerup
     * @param powerup The class of the powerup
     */
    /* package */ AEquipmentPowerup(final IServiceLocator sL,
                                    final Point point,
                                    final int maxTime,
                                    final ISprite sprite,
                                    final Animation animation,
                                    final Class<?> powerup) {
        super(sL, point, sprite, powerup);
        this.timeLimit = maxTime;
        this.defaultSprite = sprite;
        this.animation = animation;
    }

    /**
     * Sets the position of the powerup when the Doodle is equipping it.
     */
    abstract void setPosition();

    /**
     * {@inheritDoc}
     */
    @Override
    public final void perform(final PowerupOccasion occasion) {
        if (occasion == null) {
            throw new IllegalArgumentException("Occasion cannot be null");
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
     * Update method for when the flyable powerup is falling.
     */
    private void updateFalling() {
        this.applyGravity();
        this.addXPos(HORIZONTAL_SPEED);
    }

    /**
     * Applies gravity to the flyable powerup when needed.
     */
    private void applyGravity() {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        this.addYPos(this.vSpeed);
    }

    /**
     * Ends the powerup.
     */
    public final void endPowerup() {
        this.setSprite(defaultSprite);
        this.vSpeed = INITIAL_DROP_SPEED;
        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        if (doodle == null) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (this.owner == null) {
            super.getLogger().info("Doodle collided with a flyable powerup");
            this.owner = doodle;
            doodle.setPowerup(this);
        }
    }

    /**
     * Update method for when the flyable powerup is owned.
     */
    private void updateOwned() {
        timer++;

        if (timer >= timeLimit) {
            this.endPowerup();
            return;
        } else if (this.vSpeed > MAX_BOOST) {
            this.vSpeed += ACCELERATION;
        }

        if (this.timer % ANIMATION_REFRESH_RATE == 0) {
            double percentage = (double) timer / (double) timeLimit;
            int offset = ANIMATION_OFFSET_ONE;
            if (percentage > ANIMATION_PHASE_ONE && percentage < ANIMATION_PHASE_TWO) {
                offset = ANIMATION_OFFSET_TWO;
            } else if (percentage < ANIMATION_PHASE_THREE) {
                offset = ANIMATION_OFFSET_THREE;
            }

            this.spriteIndex = offset + ((spriteIndex + 1) % ANIMATION_REFRESH_RATE);
            this.setSprite(this.animation.getFromIndex(this.spriteIndex));
        }

        if (this.owner != null) {
            setPosition();
        }
    }


    /**
     * @return The owner of the Doodle, that is either {@code null} or a {@link objects.doodles.Doodle}
     */
    protected final IDoodle getOwner() {
        return owner;
    }


}

