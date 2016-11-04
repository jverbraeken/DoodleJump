package resources.animations;

import resources.IRes;
import resources.sprites.ISprite;

/**
 * Defines a {@link IAnimation} factory.
 */
public interface IAnimationFactory {
    /**
     * Loads an IAnimation with the name {@code animation}.
     *
     * @param animation The enumerator defining the requested animation
     * @return The {@link IAnimation animation} if it was found. null otherwise
     */
    IAnimation getAnimation(final IRes.Animations animation);
}
