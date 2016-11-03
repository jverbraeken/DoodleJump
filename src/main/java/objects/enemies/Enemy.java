package objects.enemies;

import objects.doodles.IDoodle;
import rendering.IRenderer;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Point;

/**
 * A sample enemy class.
 */
public final class Enemy extends AEnemy {

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
     * The amount of experience gained from killing this enemy.
     */
    private static final int EXP_AMOUNT_AT_KILL = 200;

    /**
     * OffSet of the movement from left to right.
     */
    private int offset = 0;

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
     * @param point The coordinates of the enemy
     * @param sprite The sprite of the enemy
     */
    public Enemy(final IServiceLocator sL, final Point point, final ISprite sprite) {
        super(sL, EXP_AMOUNT_AT_KILL, point, sprite, Enemy.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        return Enemy.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        IRenderer renderer = getServiceLocator().getRenderer();
        renderer.drawSprite(getSprite(), new Point((int) this.getXPos(), (int) this.getYPos()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        if (this.alive) {
            int xPos;
            if (this.movingDirection == 1) {
                xPos = (int) (this.getXPos() + 2);
                this.offset = this.offset + 2;
                if (this.offset > Enemy.MOVING_DISTANCE) {
                    this.movingDirection = 0;
                }
            } else {
                xPos = (int) (this.getXPos() - 2);
                this.offset = this.offset - 2;
                if (this.offset < -Enemy.MOVING_DISTANCE) {
                    this.movingDirection = 1;
                }
            }
            this.setXPos(xPos);
        } else {
            this.applyGravity();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
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
     * {@inheritDoc}
     */
    @Override
    public int getOffSet() {
        return this.offset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getVerticalSpeed() {
        return this.vSpeed;
    }

    /**
     * Apply gravity to the Breaking platform.
     */
    public void applyGravity() {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

    /**
     * Get if the Enemy is alive.
     * @return the attribute alive.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Set if the Enemy is alive.
     * @param alive a boolean if the Enemy is alive.
     */
    public void setAlive(final boolean alive) {
        this.alive = alive;
    }

}
