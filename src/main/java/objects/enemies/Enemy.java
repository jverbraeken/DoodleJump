package objects.enemies;

import objects.AGameObject;
import objects.doodles.IDoodle;
import rendering.IRenderer;
import resources.animations.IAnimation;
import system.IServiceLocator;

import java.awt.Point;

/**
 * A sample enemy class.
 */
public final class Enemy extends AGameObject implements IEnemy {

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
     * The type of the enemy.
     */
    private final Enemies type;

    /**
     * The animation of the enemy.
     */
    private final IAnimation animation;

    /**
     * Creates a new enemy and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param serviceLocator The service locator
     * @param point The coordinates of the enemy
     * @param type The type of the enemy
     */
    /* package */ Enemy(final IServiceLocator serviceLocator, final Point point, final Enemies type) {
        super(serviceLocator, point, serviceLocator.getSpriteFactory().getSprite(type.getAnimation().getSpriteReference(0)), Enemy.class);
        this.type = type;
        this.animation = serviceLocator.getAnimationFactory().getAnimation(this.type.getAnimation());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        return type.getBoost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        IRenderer renderer = getServiceLocator().getRenderer();
        renderer.drawSprite(this.animation.getFromIndex(0), new Point((int) this.getXPos(), (int) this.getYPos()));
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
                if (this.offset > type.getMovingDistance()) {
                    this.movingDirection = 0;
                }
            } else {
                xPos = (int) (this.getXPos() - 2);
                this.offset = this.offset - 2;
                if (this.offset < -type.getMovingDistance()) {
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
            if (doodle.getVerticalSpeed() < type.getTooFastSpeed()) {
                doodle.setVerticalSpeed(type.getTooFastSpeed());
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
    @Override
    public void applyGravity() {
        this.vSpeed += getServiceLocator().getConstants().getGravityAcceleration();
        addYPos(this.vSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAmountOfExperience() {
        return type.getExpAmountAtKill();
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
