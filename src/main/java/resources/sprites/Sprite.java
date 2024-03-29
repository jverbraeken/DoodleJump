package resources.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Class representing a sprite.
 *
 * <p><b>IMMUTABLE</b>
 */
public final class Sprite implements ISprite {

    /**
     * The name of the sprite.
     */
    private final String name;
    /**
     * The image of the sprite.
     */
    private final Image image;
    /**
     * The width and height of the sprite.
     */
    private final int width, height;
    /**
     * The ratio of the sprite.
     */
    private final double ratio;

    /**
     * Package constructor only allowing instantiations by the SpriteFactory.
     *
     * @param n The name for the sprite.
     * @param i The image for the sprite.
     */
    /* package */ Sprite(final String n, final BufferedImage i) {
        this.name = n;
        this.image = i;
        this.width = i.getWidth();
        this.height = i.getHeight();
        this.ratio = (double) height / (double) width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return this.width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return this.height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getRatio() {
        return this.ratio;
    }

}
