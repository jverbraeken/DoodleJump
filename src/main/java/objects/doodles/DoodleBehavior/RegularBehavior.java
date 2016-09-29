package objects.doodles.DoodleBehavior;

import input.Keys;
import objects.doodles.IDoodle;
import resources.sprites.ISpriteFactory;
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
     * The constructor of the regular behavior.
     *
     * @param d  The doodle this applies to.
     * @param sL the Servicelocator
     */
    public RegularBehavior(final IServiceLocator sL, final IDoodle d) {
        serviceLocator = sL;
        doodle = d;
    }

    /**
     * {@inheritDoc}
     */
    public void move(final double delta) {
        moveHorizontally(delta);
        applyGravity(delta);
        animate(delta);
    }

    /**
     * Move the Doodle along the X axis.
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
     * Animate the Doodle.
     *
     * @param delta Delta time since previous animate.
     */
    private void animate(double delta) {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        doodle.setSpritePack(spriteFactory.getDoodleSprite(getFacing()));

        // If the Doodle moves up quickly shorten its legs
        if (getVerticalSpeed() < -15) {
            doodle.setSprite(this.doodle.getSpritePack()[1]);
        } else {
            doodle.setSprite(this.doodle.getSpritePack()[0]);
        }
    }


    /**
     * Apply gravity to the Doodle.
     *
     * @param delta Delta time since previous animate.
     */
    private void applyGravity(double delta) {
        this.vSpeed += serviceLocator.getConstants().getGravityAcceleration();
        doodle.addYPos(this.vSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getVerticalSpeed() {
        return vSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVerticalSpeed(final double v) {
        vSpeed = v;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Directions getFacing() {
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
        } else if (this.rightPressed(key)) {
            this.moving = Directions.Right;
            this.facing = Directions.Right;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void keyRelease(final Keys key) {
        if (this.leftPressed(key) && this.moving == Directions.Left) {
            this.moving = null;
        } else if (this.rightPressed(key) && this.moving == Directions.Right) {
            this.moving = null;
        }
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
