package objects.enemies;

import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * A sample enemy class.
 */
public class Enemy extends AEnemy {

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
        System.out.println((int) this.getXPos() + " -=-  " + (int) this.getXPos());
        getServiceLocator().getRenderer().drawSprite(getSprite(), (int) this.getXPos(), (int) this.getYPos());
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(final IDoodle doodle) { }

}
