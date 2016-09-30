package objects.doodles.DoodleBehavior;

import input.Keys;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the space movement of the Doodle.
 */
public class SpaceBehavior implements MovementBehavior {

    /**
     * The relative speed of the Doodle.
     */
    private static final double RELATIVE_SPEED = 0.5d;
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
     * Relative gravity for the Doodle.
     */
    private static final double RELATIVE_GRAVITY = .3d;
    /**
     * The speed that is considered moving quick (changing the Doodle sprite).
     */
    private static final double QUICK_MOVING_SPEED = -15;

    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * Used to access fields of the doodle this behavior describes.
     */
    private final IDoodle doodle;
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
    private Directions moving;
    /**
     * The direction the Doodle is facing.
     */
    private Directions facing;
    /**
     * Keep track if a useful button is pressed.
     */
    private boolean pressed;

    /**
     * The constructor of the regular behavior.
     *
     * @param d  The doodle this applies to.
     * @param sL the ServiceLocator.
     */
    public SpaceBehavior(final IServiceLocator sL, final IDoodle d) {
        serviceLocator = sL;
        doodle = d;
        pressed = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void move(final double delta) {
        moveHorizontally(delta);
        applyGravity(delta);
        animate(delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getVerticalSpeed() {
        return vSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setVerticalSpeed(final double v) {
        vSpeed = RELATIVE_SPEED * v;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Directions getFacing() {
        return facing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void keyPress(final Keys key) {
        if (this.leftPressed(key)) {
            this.moving = Directions.Left;
            this.facing = Directions.Left;
            this.pressed = true;
        } else if (this.rightPressed(key)) {
            this.moving = Directions.Right;
            this.facing = Directions.Right;
            this.pressed = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void keyRelease(final Keys key) {
        if (this.leftPressed(key)) {
            this.pressed = false;
        } else if (this.rightPressed(key)) {
            this.pressed = false;
        }
    }

    /**
     * Animate the Doodle.
     *
     * @param delta Delta time since previous frame.
     */
    private void animate(final double delta) {
        // If the Doodle moves up quickly shorten its legs
        if (getVerticalSpeed() < RELATIVE_SPEED * QUICK_MOVING_SPEED) {
            doodle.setSprite(getFacing(), true);
        } else {
            doodle.setSprite(getFacing(), false);
        }
    }

    /**
     * Apply gravity to the Doodle.
     *
     * @param delta Delta time since previous frame.
     */
    private void applyGravity(final double delta) {
        this.vSpeed += RELATIVE_GRAVITY * serviceLocator.getConstants().getGravityAcceleration();
        doodle.addYPos(this.vSpeed);
    }

    /**
     * Check if the Left key for the Doodle is pressed.
     *
     * @param key The key that's pressed
     * @return A boolean indicating whether the key for Left is pressed.
     */
    private boolean leftPressed(final Keys key) {
        return key == Keys.arrowLeft
                || key == Keys.a;
    }

    /**
     * Move the Doodle along the X axis.
     *
     * @param delta Delta time since previous frame.
     */
    private void moveHorizontally(final double delta) {
        if (pressed && moving == Directions.Left && this.hSpeed > -HORIZONTAL_SPEED_LIMIT) {
            this.hSpeed -= RELATIVE_SPEED * RELATIVE_SPEED * HORIZONTAL_ACCELERATION;
        } else if (pressed && moving == Directions.Right && this.hSpeed < HORIZONTAL_SPEED_LIMIT) {
            this.hSpeed += RELATIVE_SPEED * RELATIVE_SPEED * HORIZONTAL_ACCELERATION;
        }

        doodle.addXPos((int) this.hSpeed);
    }

    /**
     * Check if the Right key for the Doodle is pressed.
     *
     * @param key The key that's released
     * @return A boolean indicating whether the key for Right is pressed.
     */
    private boolean rightPressed(final Keys key) {
        return key == Keys.arrowRight
                || key == Keys.d;
    }

}
