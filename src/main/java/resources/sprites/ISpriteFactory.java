package resources.sprites;

import objects.enemies.EEnemies;
import objects.powerups.Powerups;
import resources.IRes;
import scenes.PauseScreenModes;
import system.IFactory;

/**
 * Defines a {@link ISprite} factory.
 */
public interface ISpriteFactory extends IFactory {
    /**
     * Loads an ISprite with the name {@code ISpriteName}.
     *
     * @param sprite the enumerator defining the requested sprite
     * @return The {@link ISprite sprite} if it was found. null otherwise
     */
    ISprite getSprite(final IRes.Sprites sprite);
}
