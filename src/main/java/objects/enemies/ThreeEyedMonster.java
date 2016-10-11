package objects.enemies;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * An enemy that is pretty high, and has 3 eyes.
 */
/* Package */ class ThreeEyedMonster extends AEnemy {

    /**
     * Will move 15 pixels left and right.
     */
    private static final double MOVING_DISTANCE = 15;

    /**
     * OffSet of the movement from left to right.
     */
    private int offSet = 0;

    /**
     * Moving left = 0 and when moving right = 1.
     */
    private int movingDirection = 0;

    /**
     * Is true when the enemy has been killed. This can be
     * either by a shot or from the doodle jumping on the enemy.
     */
    private boolean alive = false;

    /**
     * Creates a new enemy and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param sL The service locator
     * @param x The X-coordinate of the enemy
     * @param y The Y-coordinate of the enemy
     * @param sprite The sprite of the enemy
     */
    /* package */ ThreeEyedMonster(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite, ThreeEyedMonster.class);
    }

    @Override
    public double getBoost() {
        return 0;
    }

    @Override
    public void render() {
        if (alive) {
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

    @Override
    public void applyGravity() {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void setAlive(final boolean alive) {

    }

    @Override
    public int getOffSet() {
        return offSet;
    }

    @Override
    public double getVerticalSpeed() {
        return 0;
    }

    @Override
    public void collidesWith(final IDoodle doodle) {

    }
}
