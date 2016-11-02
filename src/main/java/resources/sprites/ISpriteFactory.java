package resources.sprites;

import objects.powerups.Powerups;
import resources.IRes;
import resources.animations.IAnimation;
import scenes.PauseScreenModes;
import system.IFactory;

/**
 * Defines a {@link ISprite} factory.
 */
public interface ISpriteFactory extends IFactory {

    /**
     * Loads an ISprite with the name {@code sprite}.
     *
     * @param sprite the enumerator defining the requested sprite
     * @return The {@link ISprite sprite} if it was found. null otherwise
     */
    ISprite getSprite(final IRes.Sprites sprite);

    ISprite[] getGreenDoodleSprites();

    ISprite[] getRedDoodleSprites();

    ISprite[] getBlueDoodleSprites();

    ISprite[] getDigitSprites();

    ISprite getPlatformBrokenSprite(final int index);

    ISprite getPauseCoverSprite(PauseScreenModes mode);

    ISprite getCoinSprite(final int digit);

    ISprite getPowerupSprite(final Powerups powerup, final int currentPowerupLevel);
}
