package objects.enemies;

import objects.AGameObject;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;

public abstract class AEnemy extends AGameObject implements IEnemy {

    /**
     * Creates a new powerup and determines its hitbox by using the sprites dimensions automatically.
     * @param x The X-coordinate of the powerup
     * @param y The Y-coordinate of the powerup
     * @param sprite The sprite of the powerup
     */
    public AEnemy(int x, int y, ISprite sprite) {
        super(serviceLocator, x, y, sprite);
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        doodle.collide(this);
    }

}
