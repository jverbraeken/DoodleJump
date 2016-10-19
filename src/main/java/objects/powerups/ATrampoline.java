package objects.powerups;

import objects.AGameObject;
import resources.sprites.ISprite;
import system.IServiceLocator;

/**
 * Created by Michael on 10/19/2016.
 */
public abstract class ATrampoline extends AGameObject {

    /**
     * The BOOST value for the ATrampoline.
     */
    private static final double BOOST = -50;

    /**
     * The used sprite for the ATrampoline.
     */
    private ISprite usedSprite;

    public ATrampoline(final IServiceLocator sL, final int x, final int y, final ISprite defaultSprite, final ISprite usedSprite, final Class<?> powerup) {
        super(sL, x, y, defaultSprite, powerup);
        this.usedSprite = usedSprite;
    }


}
