package objects.enemies;

import objects.AGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

/**
 * Abstract implementation of an Enemy.
 */
/* package */ abstract class AEnemy extends AGameObject implements IEnemy {

    /**
     * Creates a new enemy and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param sL         The service locator.
     * @param point      The coordinates of the enemy.
     * @param sprite     The sprite of the enemy.
     * @param enemyClass The class of the enemy.
     */
    /* package */ AEnemy(final IServiceLocator sL, Point point, final ISprite sprite, final Class<?> enemyClass) {
        super(sL, point, sprite, enemyClass);
    }

}
