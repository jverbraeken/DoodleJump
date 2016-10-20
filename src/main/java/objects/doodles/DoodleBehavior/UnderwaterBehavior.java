package objects.doodles.DoodleBehavior;

import input.Keys;
import objects.doodles.IDoodle;
import objects.powerups.IPowerup;
import objects.powerups.PowerupOccasion;
import system.IServiceLocator;

import java.util.HashMap;

/**
 * This class describes the underwater movement of the Doodle.
 */
public class UnderwaterBehavior implements MovementBehavior {

    /**
     * The relative speed of the doodle.
     */
    private static final double RELATIVE_SPEED = 0.5d;
    /**
     * The slow caused by the water.
     */
    private static final double SLOWING = 0.7;
    /**
     * Standard speed limit for the Doodle.
     */
    private static final double STANDARD_SPEED_LIMIT = 14d;
    /**
     * Horizontal speed limit for the Doodle.
     */
    private static final double HORIZONTAL_SPEED_LIMIT = STANDARD_SPEED_LIMIT;
    /**
     * Horizontal acceleration for the Doodle.
     */
    private static final double HORIZONTAL_ACCELERATION = .2d;
    /**
     * The threshold the Doodle for it to show to be jumping.
     */
    private static final double JUMPING_THRESHOLD = -15;
    /**
     * Relative gravity for the Doodle.
     */
    private static final double RELATIVE_GRAVITY = .3d;

    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * Used to access fields of the Doodle this behavior describes.
     */
    private final IDoodle doodle;
    /**
     * HashMaps for the actions performed by the Doodle when a key is pressed/released.
     */
    private final HashMap<Keys, Runnable> keyPressActions = new HashMap<>(), keyReleaseActions = new HashMap<>();
    /**
     * Current horizontal speed for the Doodle.
     */
    private double hSpeed = 0d;
    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;
    /**
     * The direction the Doodle is moving towards.
     */
    private boolean movingLeft = false, movingRight = false;
    /**
     * The direction the Doodle is facing.
     */
    private Directions facing = Directions.Left;

    /**
     * The constructor of the regular behavior.
     *
     * @param d The doodle this applies to.
     * @param sL the ServiceLocator.
     */
    public UnderwaterBehavior(final IServiceLocator sL, final IDoodle d) {
        this.serviceLocator = sL;
        this.doodle = d;

        this.keyPressActions.put(d.getKeyLeft(), () -> {
            this.movingLeft = true;
            this.movingRight = false;
            this.facing = Directions.Left; });
        this.keyPressActions.put(d.getKeyRight(), () -> {
            this.movingLeft = false;
            this.movingRight = true;
            this.facing = Directions.Right; });
        this.keyReleaseActions.put(d.getKeyLeft(), () -> {
            this.movingLeft = false;
            this.hSpeed = UnderwaterBehavior.SLOWING * this.hSpeed; });
        this.keyReleaseActions.put(d.getKeyLeft(), () -> {
            this.movingRight = false;
            this.hSpeed = UnderwaterBehavior.SLOWING * this.hSpeed; });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Runnable keyPress(final Keys key)  {
        return this.keyPressActions.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Runnable keyRelease(final Keys key)  {
        return this.keyReleaseActions.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void move(final double delta) {
        this.animate(delta);
        this.applyGravity(delta);
        this.moveHorizontally(delta);
        this.doodle.getPowerup().perform(PowerupOccasion.constant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Directions getFacing() {
        return this.facing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getJumpingThreshold() {
        return UnderwaterBehavior.JUMPING_THRESHOLD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getVerticalSpeed() {
        return this.vSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setVerticalSpeed(final double v) {
        this.vSpeed = UnderwaterBehavior.RELATIVE_SPEED * v;
    }

    /**
     * Animate the Doodle.
     * @param delta Delta time since previous animate.
     */
    private void animate(final double delta) {
        this.doodle.updateActiveSprite();
    }

    /**
     * Apply gravity to the Doodle.
     * @param delta Delta time since previous animate.
     */
    private void applyGravity(final double delta) {
        this.vSpeed += UnderwaterBehavior.RELATIVE_GRAVITY * this.serviceLocator.getConstants().getGravityAcceleration();
        this.doodle.addYPos(this.vSpeed);
    }

    /**
     * Move the Doodle along the X axis.
     * @param delta the time used in a frame.
     */
    private void moveHorizontally(final double delta) {
        if (this.movingLeft) {
            if (this.hSpeed > -HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed -= RELATIVE_SPEED * HORIZONTAL_ACCELERATION;
            }
        } else if (this.movingRight) {
            if (this.hSpeed < HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed += RELATIVE_SPEED * HORIZONTAL_ACCELERATION;
            }
        }

        doodle.addXPos((int) this.hSpeed);
    }

}
