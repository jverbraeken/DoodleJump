package objects.doodles.DoodleBehavior;

import input.KeyCode;
import input.Keys;
import objects.doodles.IDoodle;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

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
     * Horizontal acceleration for the Doodle.
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
     * Current horizontal speed for the Doodle.
     */
    private double hSpeed = 0d;
    /**
     * Current vertical speed for the Doodle.
     */
    private double vSpeed = 0d;
    /**
     * Used to access fields of the Doodle this behavior describes.
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
     * Keep track if a useful button is pressed.
     */
    private boolean pressed;

    /**
     * The constructor of the regular behavior.
     *
     * @param d The doodle this applies to.
     * @param sL the ServiceLocator.
     */
    public UnderwaterBehavior(final IServiceLocator sL, final IDoodle d) {
        serviceLocator = sL;
        doodle = d;
        pressed = false;
    }

    /** {@inheritDoc} */
    @Override
    public final void move(final double delta) {
        moveHorizontally(delta);
        applyGravity(delta);
        animate(delta);
    }

    /** {@inheritDoc} */
    @Override
    public final double getVerticalSpeed() {
        return vSpeed;
    }

    /** {@inheritDoc} */
    @Override
    public final void setVerticalSpeed(final double v) {
        vSpeed = RELATIVE_SPEED * v;
    }

    /** {@inheritDoc} */
    @Override
    public final Directions getFacing() {
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
            hSpeed = SLOWING * hSpeed;
        } else if (this.rightPressed(keyCode)) {
            this.pressed = false;
            hSpeed = SLOWING * hSpeed;

        }
    }

    /**
     * Animate the Doodle.
     * @param delta Delta time since previous animate.
     */
    private void animate(final double delta) {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        doodle.setSpritePack(spriteFactory.getDoodleSprite(getFacing()));

        // If the Doodle moves up quickly shorten its legs
        if (getVerticalSpeed() < RELATIVE_SPEED * JUMPING_THRESHOLD) {
            doodle.setSprite(this.doodle.getSpritePack()[1]);
        } else {
            doodle.setSprite(this.doodle.getSpritePack()[0]);
        }
    }

    /**
     * Apply gravity to the Doodle.
     * @param delta Delta time since previous animate.
     */
    private void applyGravity(final double delta) {
        this.vSpeed += RELATIVE_GRAVITY * serviceLocator.getConstants().getGravityAcceleration();
        doodle.addYPos(this.vSpeed);
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
     * Move the Doodle along the X axis.
     * @param delta the time used in a frame.
     */
    private void moveHorizontally(final double delta) {
        if (pressed && moving == Directions.Left) {
            if (this.hSpeed > -HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed -= RELATIVE_SPEED * HORIZONTAL_ACCELERATION;
            }
        } else if (pressed && moving == Directions.Right) {
            if (this.hSpeed < HORIZONTAL_SPEED_LIMIT) {
                this.hSpeed += RELATIVE_SPEED * HORIZONTAL_ACCELERATION;
            }
        }

        doodle.addXPos((int) this.hSpeed);
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
