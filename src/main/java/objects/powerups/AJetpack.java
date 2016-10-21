package objects.powerups;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Created by Michael on 10/21/2016.
 */
public abstract class AJetpack extends APowerup implements IEquipmentPowerup{
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
    private ISprite defaultSprite;

    /**
     * The sprites for an active rocket.
     */
    private ISprite[] spritePack;
    /**
     * The Doodle that owns this Jetpack.
     */
    private IDoodle owner;

    /**
     * The maximum time the Jetpack is active.
     */
    private int timeLimit;
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
    public AJetpack(final IServiceLocator sL, final int x, final int y, final int maxTime, final ISprite defaultSprite, final ISprite[] sprites, final Class<?> powerup) {
        super(sL, x, y, defaultSprite, Jetpack.class);
        this.timeLimit = maxTime;
        this.defaultSprite = defaultSprite;
        this.spritePack = sprites;
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
        if (this.owner.equals(null)) {
            throw new IllegalArgumentException("Owner cannot be null");
        }

        if (occasion.equals(PowerupOccasion.constant)) {
            this.owner.setVerticalSpeed(this.vSpeed);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if (doodle.equals(null)) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }

        if (this.owner == null) {   // For some reason the game crashes upon collision when equals method is
                                    // used to check is the value of owner's address is null.
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
            this.setSprite(this.spritePack[this.spriteIndex]);
        }

        setPosition(owner);
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
    @Override
    public void endPowerup() {
        this.setSprite(defaultSprite);
        this.vSpeed = INITIAL_DROP_SPEED;

        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
    }

    abstract void setPosition(IDoodle doodle);
}
