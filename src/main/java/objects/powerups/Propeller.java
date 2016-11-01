package objects.powerups;

import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
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
    private final double maxBoost;
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
     * The sprites for an active Propeller.
     */
    private static volatile ISprite[] spritePack = null;
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
     * @param point  - The location for the Propeller.
     */
    /* package */ Propeller(final IServiceLocator sL, final Point point, final PhysicsData physicsData, final DrawingData drawingData, final int level) {
        super(sL, point, sL.getSpriteFactory().getSprite(Powerups.propeller.getSprite(level)), Propeller.class);
        if (Propeller.spritePack == null) {
            synchronized (this) {
                if (Propeller.spritePack == null) {
                    Propeller.spritePack = sL.getSpriteFactory().getPropellerActiveSprites();
                }
            }
        }
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
        this.setSprite(getServiceLocator().getSpriteFactory().getPowerupSprite(Powerups.propeller, 1));
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
        } else if (this.vSpeed > maxBoost) {
            this.vSpeed += acceleration;
        }

        if (this.timer % animationRefreshRate == 0) {
            this.spriteIndex = (spriteIndex + 1) % Propeller.spritePack.length;
            this.setSprite(Propeller.spritePack[this.spriteIndex]);
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

    public class PhysicsData {
        private final double acceleration;
        private final double initialDropSpeed;
        private final double maxBoost;
        private final double horizontalSpeed;

        public PhysicsData(final double acceleration, final double initialDropSpeed, final double maxBoost, final double horizontalSpeed) {
            this.acceleration = acceleration;
            this.initialDropSpeed = initialDropSpeed;
            this.maxBoost = maxBoost;
            this.horizontalSpeed = horizontalSpeed;
        }
    }

    public class DrawingData {
        private final int maxTimer;
        private final int ownedYOffset;
        private final int animationRefreshRate;

        public DrawingData(final int maxTimer, final int ownedYOffset, final int animationRefreshRate) {
            this.maxTimer = maxTimer;
            this.ownedYOffset = ownedYOffset;
        }
    }
}