package objects.doodles.DoodleBehavior;

import input.KeyCode;
import input.Keys;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * This class describes the regular movement of the Doodle.
 */
public class RegularBehavior implements MovementBehavior {

    /**
     * Standard speed limit for the Doodle.
     */
    private final double STANDARD_SPEED_LIMIT = 6d;
    /**
     * Horizontal speed limit for the Doodle.
     */
    private final double HORIZONTAL_SPEED_LIMIT = STANDARD_SPEED_LIMIT;
    /**
     * Horizontal acceleration for the Doodle.
     */
    private final double HORIZONTAL_ACCELERATION = .5d;

    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
    /**
     * Current horizontal speed for the Doodle.
     */
    private double hSpeed = 0d;
    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;
    /**
     * Used to access fields of the doodle this behavior describes.
     */
    private final IDoodle doodle;
    /**
     * The direction the Doodle is moving towards.
     */
    private Directions moving;
    /**
     * The direction the Doodle is facing.
     */
    private Directions facing;

    /**
     * The constructor of the regular behavior.
     *
     * @param d The Doodle this applies to.
     * @param sL the ServiceLocator.
     */
    public RegularBehavior(final IDoodle d, final IServiceLocator sL) {
        serviceLocator = sL;
        doodle = d;
    }

    /** {@inheritDoc} */
    @Override
    public void move(final double delta){
        moveHorizontally(delta);
        applyGravity(delta);
    }

    /** {@inheritDoc} */
    @Override
    public double getVerticalSpeed() {
        return vSpeed;
    }

    /** {@inheritDoc} */
    @Override
    public void setVerticalSpeed(final double v) {
        vSpeed = v;
    }

    /** {@inheritDoc} */
    @Override
    public Directions getFacing() {
        return facing;
    }

    /** {@inheritDoc} */
    @Override
    public final void keyPress(final int keyCode) {
        if (this.leftPressed(keyCode)) {
            this.moving = Directions.Left;
            this.facing = Directions.Left;
        } else if (this.rightPressed(keyCode)) {
            this.moving = Directions.Right;
            this.facing = Directions.Right;
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void keyRelease(final int keyCode) {
        if (this.leftPressed(keyCode) && this.moving == Directions.Left) {
            this.moving = null;
        } else if (this.rightPressed(keyCode) && this.moving == Directions.Right) {
            this.moving = null;
        }
    }

    /**
     * Check if the Left key for the Doodle is pressed.
     *
     * @param keyCode The keyCode of the key.
     * @return A boolean indicating whether the key for Left is pressed.
     */
    private boolean leftPressed(final int keyCode) {
        return keyCode == KeyCode.getKeyCode(Keys.arrowLeft)
                || keyCode == KeyCode.getKeyCode(Keys.a);
    }

    /**
     * Check if the Right key for the Doodle is pressed.
     *
     * @param keyCode The keyCode of the key.
     * @return A boolean indicating whether the key for Right is pressed.
     */
    private boolean rightPressed(final int keyCode) {
        return keyCode == KeyCode.getKeyCode(Keys.arrowRight)
                || keyCode == KeyCode.getKeyCode(Keys.d);
    }

    /**
     * Move the Doodle along the X axis.
     *
     * @param delta The time since the previous frame.
     */
    private void moveHorizontally(final double delta) {
        if (moving == Directions.Left) {
            if (this.hSpeed > -this.HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed -= this.HORIZONTAL_ACCELERATION;
            }
        } else if (moving == Directions.Right) {
            if (this.hSpeed < this.HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed += this.HORIZONTAL_ACCELERATION;
            }
        } else {
            if (this.hSpeed < 0) {
                this.hSpeed += this.HORIZONTAL_ACCELERATION;
            } else if (this.hSpeed > 0) {
                this.hSpeed -= this.HORIZONTAL_ACCELERATION;
            }
        }

        doodle.addXPos((int) this.hSpeed);
    }

    /**
     * Apply gravity to the Doodle.
     *
     * @param delta Delta time since previous animate.
     */
    private void applyGravity(final double delta) {
        this.vSpeed += serviceLocator.getConstants().getGravityAcceleration();
        doodle.addYPos(this.vSpeed);
    }

}
