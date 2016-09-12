package resources.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * <b>Immutable</b>
 */
public class Sprite implements ISprite {
    private final Image image;
    private final int width, height;
    private final double ratio;

    /* package */ Sprite(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.ratio = (double) height / (double) width;
    }

    /** {@inheritDoc} */
    @Override
    public Image getImage() {
        return image;
    }

    /** {@inheritDoc} */
    @Override
    public int getWidth() {
        return width;
    }

    /** {@inheritDoc} */
    @Override
    public int getHeight() {
        return height;
    }

    /** {@inheritDoc} */
    @Override
    public double getRatio() {
        return ratio;
    }
}
