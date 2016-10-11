package objects.enemies;

import objects.doodles.IDoodle;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * A sample enemy class.
 */
public class Enemy extends AEnemy {
    /**
     * The boost the Doodle gets from colliding with the Enemy.
     */
    private static final double BOOST = -22;

    /**
     * When the Enemy is hit, the Doodle shouldn't go faster than this.
     */
    private static final double TOO_FAST_SPEED = -5;

    /**
     * Will move 15 pixels left and right.
     */
    private static final double MOVING_DISTANCE = 15;

    /**
     * OffSet of the movement from left to right.
     */
    private int offSet = 0;

    /**
     * Current vertical speed for the Enemy.
     */
    private double vSpeed = 0d;

    /**
     * Moving left = 0 and when moving right = 1.
     */
    private int movingDirection = 0;

    /**
     * Is false when the enemy has been killed. This can be
     * either by a shot or from the doodle jumping on the enemy.
     */
    private boolean alive = true;

    /**
     * Creates a new enemy and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param sL The service locator
     * @param x The X-coordinate of the enemy
     * @param y The Y-coordinate of the enemy
     * @param sprite The sprite of the enemy
     */
    public Enemy(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite, Enemy.class);
    }

    /** {@inheritDoc} */
    @Override
    public final double getBoost() {
        return this.BOOST;
    }

    /** {@inheritDoc} */
    @Override
    public final void render() {
        IRenderer renderer = getServiceLocator().getRenderer();
        renderer.drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /** {@inheritDoc} */
    @Override
    public final void update(final double delta) {
        if (alive) {
            int xPos;
            if (movingDirection == 1) {
                xPos = (int) (this.getXPos() + 2);
                offSet = offSet + 2;
                if (offSet > MOVING_DISTANCE) {
                    movingDirection = 0;
                }
            } else {
                xPos = (int) (this.getXPos() - 2);
                offSet = offSet - 2;
                if (offSet < -MOVING_DISTANCE) {
                    movingDirection = 1;
                }
            }
            this.setXPos(xPos);
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        if (doodle.getVerticalSpeed() > 0) {
            alive = false;
            vSpeed = doodle.getVerticalSpeed();
            doodle.collide(this);
        } else if (alive) {
            if (doodle.getVerticalSpeed() < TOO_FAST_SPEED) {
                doodle.setVerticalSpeed(TOO_FAST_SPEED);
            }
            doodle.setAlive(false);
        }
    }

    /**
     * Apply gravity to the Breaking platform.
     */
    public final void applyGravity() {
        vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

    /**
     * Get if the Enemy is alive.
     * @return the attribute alive.
     */
    public final boolean isAlive() {
        return alive;
    }

    /**
     * Set if the Enemy is alive.
     * @param alive a boolean if the Enemy is alive.
     */
    public final void setAlive(final boolean alive) {
        this.alive = alive;
    }

    /**
     * Returns the offSet of this Enemy.
     * @return the attribute offSet.
     */
    final int getOffSet() {
        return offSet;
    }

    /**
     * Returns the vSpeed of this Enemy.
     * @return the attribute vSpeed.
     */
    final double getVerticalSpeed() {
        return vSpeed;
    }

}
