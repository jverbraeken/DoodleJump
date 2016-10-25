package objects.enemies;

import objects.AGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Abstract implementation of an Enemy.
 */
public abstract class AEnemy extends AGameObject implements IEnemy {

    private final int expAmountAtKill;

    /**
     * Creates a new enemy and determines its hitbox by using the sprites dimensions automatically.
     *
     * @param sL         The service locator.
     * @param x          The X-coordinate of the enemy.
     * @param y          The Y-coordinate of the enemy.
     * @param sprite     The sprite of the enemy.
     * @param enemyClass The class of the enemy.
     */
    /* package */ AEnemy(final IServiceLocator sL, final int expAmountAtKill, final int x, final int y, final ISprite sprite, final Class<?> enemyClass) {
        super(sL, x, y, sprite, enemyClass);
        this.expAmountAtKill = expAmountAtKill;
    }


    /**
     * Returns the amount of experience won by killing this enemy.
     * @return the amount of experience.
     */
    public final int getAmountOfExperience() {
        return expAmountAtKill;
    }

}
