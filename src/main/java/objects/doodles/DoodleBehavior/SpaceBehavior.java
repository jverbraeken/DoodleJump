package objects.doodles.DoodleBehavior;

import input.Keys;
import objects.doodles.IDoodle;
import objects.powerups.IPowerup;
import objects.powerups.PowerupOccasion;
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
    private Directions facing = Directions.Left;
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

        IPowerup powerup = this.doodle.getPowerup();
        if (powerup != null) {
            powerup.perform(PowerupOccasion.constant);
        }
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
     * Returns if a key is pressed.
     *
     * @return if a key is pressed.
     */
    public final boolean getPressed() {
        return pressed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Runnable keyPress(final Keys key) {
        return null;
        /*if (this.isLeftPressed(key)) {
            this.moving = Directions.Left;
            this.facing = Directions.Left;
            this.pressed = true;
        } else if (this.isRightPressed(key)) {
            this.moving = Directions.Right;
            this.facing = Directions.Right;
            this.pressed = true;
        }*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Runnable keyRelease(final Keys key) {
        return null;
        /*if (this.isLeftPressed(key)) {
            this.pressed = false;
        } else if (this.isRightPressed(key)) {
            this.pressed = false;
        }*/
    }

    /**
     * Animate the Doodle.
     *
     * @param delta Delta time since previous frame.
     */
    private void animate(final double delta) {
        // If the Doodle moves up quickly shorten its legs
        if (getVerticalSpeed() < RELATIVE_SPEED * JUMPING_THRESHOLD) {
            doodle.setSprite(true);
        } else {
            doodle.setSprite(false);
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
    private boolean isLeftPressed(final Keys key) {
        Keys keys = this.doodle.getKeyLeft();
        return key == keys;
    }

    /**
     * Check if the Right key for the Doodle is pressed.
     *
     * @param key The key that's released
     * @return A boolean indicating whether the key for Right is pressed.
     */
    private boolean isRightPressed(final Keys key) {
        Keys keys = this.doodle.getKeyRight();
        return key == keys;
    }

    /**
     * Move the Doodle along the X axis.
     *
     * @param delta Delta time since previous frame.
     */
    private void moveHorizontally(final double delta) {
        if (pressed && moving == Directions.Left) {
            if (this.hSpeed > -HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed -= RELATIVE_SPEED * RELATIVE_SPEED * HORIZONTAL_ACCELERATION;
            }
        } else if (pressed && moving == Directions.Right) {
            if (this.hSpeed < HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed += RELATIVE_SPEED * RELATIVE_SPEED * HORIZONTAL_ACCELERATION;
            }
        }

        doodle.addXPos((int) this.hSpeed);
    }


}
