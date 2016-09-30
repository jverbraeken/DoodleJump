package objects.enemies;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Abstract implementation of an Enemy.
 */
/* package */ abstract class AEnemy extends AGameObject implements IEnemy {

    /**
     * Creates a new enemy and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param sL         The service locator.
     * @param x          The X-coordinate of the enemy.
     * @param y          The Y-coordinate of the enemy.
     * @param sprite     The sprite of the enemy.
     * @param enemyClass The class of the enemy.
     */
    /* package */ AEnemy(final IServiceLocator sL, final int x, final int y, final ISprite sprite, final Class<?> enemyClass) {
        super(sL, x, y, sprite, enemyClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        doodle.collide(this);
    }

}
