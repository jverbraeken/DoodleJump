package objects.powerups;

import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import resources.animations.IAnimation;
import system.IServiceLocator;

import java.awt.Point;

/**
 * This class describes the behaviour of the Propeller powerup.
 */
/* package */ final class Propeller extends APowerup implements IEquipmentPowerup {

    /**
     * The acceleration provided by the Propeller.
     */
    private final double acceleration;
    /**
     * The boost for the Propeller when it is being dropped.
     */
    private final double initialDropSpeed;
    /**
     * The boost the Propeller gives.
     */
    private final double boost;
    /**
     * The horizontal speed for a Propeller.
     */
    private final double horizontalSpeed;
    /**
     * The maximum time the Propeller is active.
     */
    private final int maxTimer;
    /**
     * Y offset for drawing the Propeller when on Doodle.
     */
    private final int ownedYOffset;
    /**
     * The refresh rate for the active animation.
     */
    private final int animationRefreshRate;

    /**
     * The animation for an active Propeller.
     */
    private final IAnimation animation;
    /**
     * The current level of the powerup.
     */
    private final int level;
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
     * @param serviceLocator The service locator
     * @param point          The location for the powerup
     * @param level          The level of the powerup
     */
    /* package */ Propeller(final IServiceLocator serviceLocator, final Point point, final int level) {
        super(serviceLocator, point, Powerups.propeller, level);
        final Powerups type = Powerups.propeller;
        this.animation = serviceLocator.getAnimationFactory().getAnimation(type.getAnimation(level));
        this.acceleration = type.getAcceleration(level);
        this.initialDropSpeed = type.getInitDropSpeed(level);
        this.boost = type.getBoost(level);
        this.horizontalSpeed = type.getHorSpeed(level);
        this.maxTimer = type.getMaxTimeInAir(level);
        this.ownedYOffset = type.getOwnedYOffset(level);
        this.animationRefreshRate = type.getAnimationRefreshRate(level);
        this.level = level;
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

        // The game crashes upon collision when equals method is used to check if the value of owner's address
        // is the same as a null reference resulting in a NullPointerReference.
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
    public void endPowerup() {
        this.setSprite(getServiceLocator().getSpriteFactory().getSprite(Powerups.propeller.getSprite(level)));
        this.vSpeed = initialDropSpeed;

        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPositionOnPlatform(final IPlatform platform) {
        super.setPositionOnPlatformRandom(platform);
    }

    /**
     * Update method for when the Propeller is owned.
     */
    private void updateOwned() {
        this.timer++;

        if (this.timer >= maxTimer) {
            this.endPowerup();
            return;
        } else if (this.vSpeed > boost) {
            this.vSpeed += acceleration;
        }

        if (this.timer % animationRefreshRate == 0) {
            this.spriteIndex = (spriteIndex + 1) % this.animation.getLength();
            this.setSprite(this.animation.getFromIndex(this.spriteIndex));
        }

        if (this.owner != null) {
            this.setXPos((int) this.owner.getXPos()
                    + this.getSprite().getWidth() / 2);
            this.setYPos((int) this.owner.getYPos()
                    + this.getSprite().getHeight() / 2
                    + ownedYOffset);
        }
    }

    /**
     * Update method for when the Propeller is falling.
     */
    private void updateFalling() {
        this.applyGravity();
        this.addXPos(horizontalSpeed);
    }

    /**
     * Applies gravity to the Propeller when needed.
     */
    private void applyGravity() {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        this.addYPos(this.vSpeed);
    }
}