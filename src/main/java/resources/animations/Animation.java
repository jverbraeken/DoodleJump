package resources.animations;

import resources.sprites.ISprite;

/**
 * An animation.
 */
public final class Animation implements IAnimation {

    /**
     * The frames of which the animation consists.
     */
    private final ISprite[] sprites;

    /**
     * Prevents instantiation from outside the package.
     *
     * @param sprites The frames of the animation
     */
    /* package */ Animation(final ISprite[] sprites) {
        this.sprites = sprites;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getFromIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("The animation index must be greater than or equal to 0");
        }
        if (index > sprites.length) {
            throw new IllegalArgumentException("The animation index requested: " + index + " cannot exceed the maximum animation index " + sprites.length);
        }
        return sprites[index];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {
        return this.sprites.length;
    }
}
