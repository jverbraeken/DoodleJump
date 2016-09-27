package objects.doodles.DoodleBehavior;

import input.KeyCode;
import input.Keys;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * Created by Nick on 27-9-2016.
 */
public class SpaceBehavior implements movementBehavior{

    /**
     * The relative speed of the doodle.
     */
    private static final double RELATIVE_SPEED = 0.5d;
    /**
     * Current horizontal speed for the Doodle.
     */
    private double hSpeed = 0d;
    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;

    /**
     * Standard speed limit for the Doodle.
     */
    private final double STANDARD_SPEED_LIMIT = 20d;

    /**
     * Horizontal speed limit for the Doodle.
     */
    private final double HORIZONTAL_SPEED_LIMIT = STANDARD_SPEED_LIMIT;
    /**
     * Horizontal acceleration for the Doodle.
     */
    private final double HORIZONTAL_ACCELERATION = 3d;
    /**
     * Relative gravity for the Doodle.
     */
    private final double RELATIVE_GRAVITY = .3d;
    /**
     * Used to access all services.
     */
    private final IServiceLocator serviceLocator;
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
    private boolean pressed;

    /**
     * The constructor of the regular behavior.
     * @param d The doodle this applies to.
     * @param sL the Servicelocator
     */
    public SpaceBehavior(final IDoodle d, final IServiceLocator sL){
        serviceLocator = sL;
        doodle = d;
        pressed = false;
    }

    public void move(final double delta){
        moveHorizontally(delta);
        applyGravity(delta);
    }

    /**
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally(final double delta) {
        if (pressed && moving == Directions.Left) {
            if (this.hSpeed > -this.HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed -= RELATIVE_SPEED*RELATIVE_SPEED*this.HORIZONTAL_ACCELERATION;
            } else if (this.hSpeed > -this.HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed -= this.HORIZONTAL_ACCELERATION;
            }
        } else if (pressed && moving == Directions.Right) {
            if (this.hSpeed < this.HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed += RELATIVE_SPEED*RELATIVE_SPEED*this.HORIZONTAL_ACCELERATION;
            } else if (this.hSpeed < this.HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed += this.HORIZONTAL_ACCELERATION;
            }
        }

        doodle.addXPos((int) this.hSpeed);
    }


    /**
     * Apply gravity to the Doodle.
     * @param delta Delta time since previous animate.
     */
    private void applyGravity(double delta) {
        this.vSpeed += RELATIVE_GRAVITY*serviceLocator.getConstants().getGravityAcceleration();
        doodle.addYPos(this.vSpeed);
    }

    /** {@inheritDoc} */
    @Override
    public double getVerticalSpeed() {
        return vSpeed;
    }

    /** {@inheritDoc} */
    @Override
    public void setVerticalSpeed(final double v) {
        vSpeed = RELATIVE_SPEED*v;
    }

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
            this.pressed = true;
        } else if (this.rightPressed(keyCode)) {
            this.moving = Directions.Right;
            this.facing = Directions.Right;
            this.pressed = true;
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void keyRelease(final int keyCode) {
        if (this.leftPressed(keyCode)) {
            this.pressed = false;
        } else if (this.rightPressed(keyCode)) {
            this.pressed = false;
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
}
