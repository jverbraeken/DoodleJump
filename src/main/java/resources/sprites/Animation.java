package resources.sprites;

public final class Animation implements IAnimation {
    private final ISprite[] sprites;

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
