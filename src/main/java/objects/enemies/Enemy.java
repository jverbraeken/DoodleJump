package objects.enemies;

import objects.AGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the behavior of an enemy.
 */
public abstract class Enemy extends AGameObject implements IEnemy {
    public Enemy(final IServiceLocator serviceLocator, final int x, final int y, final ISprite sprite) {
        super(serviceLocator, x, y, sprite);
    }
}
