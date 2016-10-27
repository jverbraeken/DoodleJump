package objects.powerups;

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
    private static final double ACCELERATION = -1d;
    /**
     * The boost for the Propeller when it is being dropped.
     */
    private static final double INITIAL_DROP_SPEED = -20d;
    /**
     * The boost the Propeller gives.
     */
    private static final double MAX_BOOST = -20d;
    /**
     * The horizontal speed for a Propeller.
     */
    private static final double HORIZONTAL_SPEED = 1.2d;
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
     * Angle per frame when falling.
     */
    private static final double ANGLE_PER_FRAME = 0.05;

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
     * The current rotation angle of the jetpack.
     */
    private double theta = 0;

    /**
     * Propeller constructor.
     *
     * @param sL - The Games service locator.
     * @param x  - The X location for the Propeller.
     * @param y  - The Y location for the Propeller.
     */
    /* package */ Propeller(final IServiceLocator sL, final int x, final int y) {
        super(sL, x, y, sL.getSpriteFactory().getPowerupSprite(Powerups.propeller, 1), Propeller.class);
        Propeller.spritePack = sL.getSpriteFactory().getPropellerActiveSprites();
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
        getServiceLocator().getRenderer().drawSprite(this.getSprite(), new Point((int) this.getXPos(), (int) this.getYPos()), this.theta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endPowerup() {
        this.setSprite(getServiceLocator().getSpriteFactory().getPowerupSprite(Powerups.propeller, 1));
        this.vSpeed = INITIAL_DROP_SPEED;

        this.owner.removePowerup(this);
        this.owner.getWorld().addDrawable(this);
        this.owner.getWorld().addUpdatable(this);
        this.owner = null;
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

        if (this.owner != null) {
            this.setXPos((int) this.owner.getXPos() + (this.getSprite().getWidth() / 2));
            this.setYPos((int) this.owner.getYPos() + (this.getSprite().getHeight() / 2) + OWNED_Y_OFFSET);
        }
    }

    /**
     * Update method for when the Propeller is falling.
     */
    private void updateFalling() {
        this.applyGravity();
        this.addXPos(HORIZONTAL_SPEED);
        this.theta += Propeller.ANGLE_PER_FRAME;
    }

    /**
     * Applies gravity to the Propeller when needed.
     */
    private void applyGravity() {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        this.addYPos(this.vSpeed);
    }

}
