package objects.powerups;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Class that describes the behaviour of subclasses of AJetpack.
 */
public abstract class AJetpack extends APowerup implements IEquipmentPowerup{

    /**
     * The boost the AJet object provides.
     */
    private static final double MAX_BOOST = -25d;
    /**
     * The acceleration provided by the AJet.
     */
    private static final double ACCELERATION = -2d;
    /**
     * The boost for the AJet when it is being dropped.
     */
    private static final double INITIAL_DROP_SPEED = -25d;
    /**
     * The horizontal speed for a AJet.
     */
    private static final double HORIZONTAL_SPEED = 1.2d;
    /**
     * Percentage for the initial phase of the AJet animation.
     */
    private static final double ANIMATION_PHASE_ONE = 0.1d;
    /**
     * Percentage for the second phase of the AJet animation.
     */
    private static final double ANIMATION_PHASE_TWO = 0.8d;
    /**
     * Percentage for the third phase of the AJet animation.
     */
    private static final double ANIMATION_PHASE_THREE = 1d;
    /**
     * The refresh rate for the active animation.
     */
    private static final int ANIMATION_REFRESH_RATE = 3;

    /**
     * Offset for the initial phase of the AJet animation.
     */
    private static final int ANIMATION_OFFSET_ONE = 0;
    /**
     * Offset for the second phase of the AJet animation.
     */
    private static final int ANIMATION_OFFSET_TWO = 3;
    /**
     * Offset for the third phase of the AJet animation.
     */
    private static final int ANIMATION_OFFSET_THREE = 6;

    /**
     * Default sprite of this AJet object.
     */
    private ISprite defaultSprite;

    /**
     * The sprites for an active AJet.
     */
    private ISprite[] spritePack;
    /**
     * The Doodle that owns this AJet.
     */
    private IDoodle owner;

    /**
     * The maximum time the AJet is active.
     */
    private int timeLimit;
    /**
     * The active timer for the AJet.
     */
    private int timer = 0;
    /**
     * The vertical speed of the AJet.
     */
    private double vSpeed = 0d;
    /**
     * The index of the current sprite.
     */
    private int spriteIndex = 0;

    /**
     * AJetpack constructor.
     *
     * @param sL - The Game's service locator.
     * @param x - The X location for the AJetpack.
     * @param y - The Y location for the AJetpack.
     */
    public AJetpack(final IServiceLocator sL,
                final int x,
                final int y,
                final int maxTime,
                final ISprite sprite,
                final ISprite[] sprites,
                final Class<?> powerup) {
        super(sL, x, y, sprite, powerup);
        this.timeLimit = maxTime;
        this.defaultSprite = sprite;
        this.spritePack = sprites;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final void perform(final PowerupOccasion occasion) {
        if (this.owner.equals(null)) {
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
     * {@inheritDoc}
     */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        if (doodle.equals(null)) {
            throw new IllegalArgumentException("Doodle cannot be null");
        }


        // The game crashes upon collision when equals method is used to check if the value of owner's address
        // is the same as a null reference resulting in a NullPointerReference.
        if (this.owner == null) {
            getLogger().info("Doodle collided with a Jetpack");
            this.owner = doodle;

            doodle.setPowerup(this);
        }
    }

    /**
     * Update method for when the Jetpack is owned.
     */
    private final void updateOwned() {
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
     * Update method for when the AJetpack is falling.
     */
    private final void updateFalling() {
        this.applyGravity();
        this.addXPos(HORIZONTAL_SPEED);
    }

    /**
     * Applies gravity to the AJetpack when needed.
     */
    private final void applyGravity() {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        this.addYPos(this.vSpeed);
    }

    /**
     * Ends the powerup.
     */
    @Override
    public final void endPowerup() {
        this.setSprite(defaultSprite);
        this.vSpeed = INITIAL_DROP_SPEED;

        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
    }

    /**
     * Sets the position of the powerup when equipped.
     * @param doodle The doodle that is wearing this powerup.
     */
    abstract void setPosition(IDoodle doodle);


    public final IDoodle getOwner() {
        return owner;
    }

    public final void setOwner(final IDoodle doodle) {
        this.owner = doodle;
    }



}
