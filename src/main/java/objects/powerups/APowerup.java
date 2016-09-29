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
     *
     * @param sL The locator providing services to the powerup
     * @param x The X-coordinate of the powerup
     * @param y The Y-coordinate of the powerup
     * @param sprite The sprite of the powerup
     */
    public APowerup(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite);
    }

}
