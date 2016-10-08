package objects.enemies;

import resources.sprites.ISprite;
import system.IServiceLocator;

public class threeEyedMonster extends AEnemy {

    /**
     * Will move 15 pixels left and right.
     */
    private final static double movingDistance = 15;

    /**
     * OffSet of the movement from left to right.
     */
    private int offSet = 0;

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
    public threeEyedMonster(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite, threeEyedMonster.class);
    }

    @Override
    public double getBoost() {
        return 0;
    }

    @Override
    public void render() {
        if (!killed) {
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

    @Override
    public void applyGravity() {

    }

    @Override
    public boolean getKilled() {
        return false;
    }

    @Override
    public void setKilled(boolean killed) {

    }
}
