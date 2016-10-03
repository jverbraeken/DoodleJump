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
     * Will move 10 pixels left and right.
     */
    private static double movingDistance = 15;

    /**
     * OffSet of the movement from left to right.
     */
    private int offSet = 0;

    /**
     * Moving left = 0 and when moving right = 1
     */
    private int movingDirection = 0;

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
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        int xPos = 0;
        int yPos = (int) this.getYPos();
        if (movingDirection == 1) {
            xPos = (int) (this.getXPos() + 2);
            offSet = offSet + 2;
            if (offSet > movingDistance) {
                movingDirection = 0;
            }
        }
        else {
            xPos = (int) (this.getXPos() - 2);
            offSet = offSet - 2;
            if (offSet < -movingDistance) {
                movingDirection = 1;
            }
        }
        this.setXPos(xPos);
        getServiceLocator().getRenderer().drawSprite(getSprite(), xPos, yPos);
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(final IDoodle doodle) {
        System.out.println("dodo");
        Game.setPaused(true);
    }

}
