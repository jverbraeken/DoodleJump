package objects.powerups;

import objects.AGameObject;
import resources.sprites.ISprite;

public abstract class APowerup extends AGameObject implements IPowerup {

    /**
     * Creates a new powerup and determines its hitbox by using the sprites dimensions automatically.
     * @param x The X-coordinate of the powerup
     * @param y The Y-coordinate of the powerup
     * @param sprite The sprite of the powerup
     */
    public APowerup(int x, int y, ISprite sprite) {
        super(x, y, sprite);
    }

}
