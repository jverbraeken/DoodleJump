package objects.enemies;

import objects.doodles.IDoodle;
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
     * Is true when the enemy has been killed. This can be
     * either by a shot or from the doodle jumping on the enemy.
     */
    private boolean killed = false;

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
        if (killed) {
            getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
        } else {
            int xPos;
            int yPos = (int) this.getYPos();
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
            getServiceLocator().getRenderer().drawSprite(getSprite(), xPos, yPos);
        }

    }

    /** {@inheritDoc} */
    @Override
    public final void update(final double delta) {
        if (killed) {
            applyGravity();
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void collidesWith(final IDoodle doodle) {
        if (doodle.getVerticalSpeed() > 0 && !doodle.isHitByEnemy()) {
            killed = true;
            vSpeed = doodle.getVerticalSpeed();
            doodle.collide(this);
        } else if (!killed) {
            if (doodle.getVerticalSpeed() < TOO_FAST_SPEED) {
                doodle.setVerticalSpeed(TOO_FAST_SPEED);
            }
            doodle.setHitByEnemy(true);
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
     * Get if the Enemy is killed.
     * @return the attribute killed.
     */
    public final boolean getKilled() {
        return killed;
    }

    /**
     * Set if the Enemy is killed.
     * @param killed a boolean if the Enemy is killed.
     */
    public final void setKilled(final boolean killed) {
        this.killed = killed;
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
