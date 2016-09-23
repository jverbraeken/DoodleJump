package objects.powerups;

import objects.AGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * This class describes the abstract functionality of powerups.
 */
public abstract class APowerup extends AGameObject implements IPowerup {

    /**
     * Creates a new powerup and determines its hitbox by using the sprites dimensions automatically.
     * @param serviceLocator The locator providing services to the powerup
     * @param x The X-coordinate of the powerup
     * @param y The Y-coordinate of the powerup
     * @param sprite The sprite of the powerup
     */
    public APowerup(final IServiceLocator serviceLocator, int x, int y, ISprite sprite) {
        super(serviceLocator, x, y, sprite);
    }

}
