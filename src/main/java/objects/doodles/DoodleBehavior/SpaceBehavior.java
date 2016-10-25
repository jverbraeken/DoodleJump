package objects.doodles.DoodleBehavior;

import input.Keys;
import objects.doodles.IDoodle;
import objects.powerups.PowerupOccasion;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class describes the space movement of the Doodle.
 */
public final class SpaceBehavior implements MovementBehavior {

    /**
     * The relative speed of the Doodle.
     */
    private static final double RELATIVE_SPEED = 0.5d;
    /**
     * The relative speed of the Doodle squared.
     */
    private  static final double RELATIVE_SPEED_SQUARED = RELATIVE_SPEED * RELATIVE_SPEED;
    /**
     * Standard speed limit for the Doodle.
     */
    private static final double STANDARD_SPEED_LIMIT = 20d;
    /**
     * Horizontal speed limit for the Doodle.
     */
    private static final double HORIZONTAL_SPEED_LIMIT = STANDARD_SPEED_LIMIT;
    /**
     * Horizontal acceleration for the Doodle.
     */
    private static final double HORIZONTAL_ACCELERATION = 3d;
    /**
     * The threshold the Doodle for it to show to be jumping.
     */
    private static final double JUMPING_THRESHOLD = -15d;
    /**
     * Relative gravity for the Doodle.
     */
    private static final double RELATIVE_GRAVITY = .3d;

    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * Used to access fields of the doodle this behavior describes.
     */
    private final IDoodle doodle;
    /**
     * HashMaps for the actions performed by the Doodle when a key is pressed/released.
     */
    private Map<Keys, Runnable> keyPressActions, keyReleaseActions;
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
     * @param d  The doodle this applies to.
     * @param sL the ServiceLocator.
     */
    public SpaceBehavior(final IServiceLocator sL, final IDoodle d) {
        this.serviceLocator = sL;
        this.doodle = d;
        this.updateActions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateActions() {
        this.keyPressActions = new EnumMap<>(Keys.class);
        this.keyReleaseActions = new EnumMap<>(Keys.class);

        this.keyPressActions.put(this.doodle.getKeyLeft(), () -> {
            this.movingLeft = true;
            this.movingRight = false;
            this.facing = Directions.Left; });
        this.keyPressActions.put(this.doodle.getKeyRight(), () -> {
            this.movingLeft = false;
            this.movingRight = true;
            this.facing = Directions.Right; });
        this.keyReleaseActions.put(this.doodle.getKeyLeft(), () -> this.movingLeft = false);
        this.keyReleaseActions.put(this.doodle.getKeyRight(), () -> this.movingRight = false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void keyPress(final Keys key) {
        this.keyPressActions.get(key).run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void keyRelease(final Keys key) {
        this.keyReleaseActions.get(key).run();
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
        return SpaceBehavior.JUMPING_THRESHOLD;
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
        this.vSpeed = SpaceBehavior.RELATIVE_SPEED * v;
    }

    /**
     * Animate the Doodle.
     *
     * @param delta Delta time since previous frame.
     */
    private void animate(final double delta) {
        this.doodle.updateActiveSprite();
    }

    /**
     * Apply gravity to the Doodle.
     *
     * @param delta Delta time since previous frame.
     */
    private void applyGravity(final double delta) {
        final double gravityAcceleration = this.serviceLocator.getConstants().getGravityAcceleration();
        this.vSpeed += SpaceBehavior.RELATIVE_GRAVITY * gravityAcceleration * delta;
        this.doodle.addYPos(this.vSpeed);
    }

    /**
     * Move the Doodle along the X axis.
     *
     * @param delta Delta time since previous frame.
     */
    private void moveHorizontally(final double delta) {
        if (this.movingLeft && this.hSpeed > -SpaceBehavior.HORIZONTAL_SPEED_LIMIT) {
            this.hSpeed -= SpaceBehavior.RELATIVE_SPEED_SQUARED * SpaceBehavior.HORIZONTAL_ACCELERATION * delta;
        } else if (this.movingRight && this.hSpeed < SpaceBehavior.HORIZONTAL_SPEED_LIMIT) {
            this.hSpeed += SpaceBehavior.RELATIVE_SPEED_SQUARED * SpaceBehavior.HORIZONTAL_ACCELERATION * delta;
        }

        doodle.addXPos((int) this.hSpeed);
    }


}
