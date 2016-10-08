package objects.enemies;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

/**
 * A sample enemy class.
 */
public class Enemy extends AEnemy {
    /**
     * The boost the Doodle gets from colliding with the Enemy.
     */
    private static final double BOOST = -18;

    /**
     * Will move 15 pixels left and right.
     */
    private final static double movingDistance = 15;

    /**
     * OffSet of the movement from left to right.
     */
    private int offSet = 0;

    /**
     * Current vertical speed for the Enemy.
     */
    private double vSpeed = 0d;

    /**
     * Moving left = 0 and when moving right = 1
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
    public void render() {
        if (killed) {
            getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
        }
        else {
            int xPos;
            int yPos = (int) this.getYPos();
            if (movingDirection == 1) {
                xPos = (int) (this.getXPos() + 2);
                offSet = offSet + 2;
                if (offSet > movingDistance) {
                    movingDirection = 0;
                }
            } else {
                xPos = (int) (this.getXPos() - 2);
                offSet = offSet - 2;
                if (offSet < -movingDistance) {
                    movingDirection = 1;
                }
            }
            this.setXPos(xPos);
            getServiceLocator().getRenderer().drawSprite(getSprite(), xPos, yPos);
        }

    }

    /** {@inheritDoc} */
    @Override
    public void update(double delta) {
        if (killed) {
            applyGravity();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(final IDoodle doodle) {
        if(doodle.getVerticalSpeed() > 0 && !doodle.isHitByEnemy()) {
            System.out.println("hit van boven");
            killed = true;
            vSpeed = doodle.getVerticalSpeed();
            doodle.collide(this);
        }
        else if (!killed) {
            System.out.println("dodo");
            if (doodle.getVerticalSpeed() < -5) {
                doodle.setVerticalSpeed(-5);
            }
            doodle.setHitByEnemy(true);
        }
    }

    /**
     * Apply gravity to the Breaking platform.
     */
    private void applyGravity() {
        vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

}
