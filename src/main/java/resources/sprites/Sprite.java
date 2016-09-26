package resources.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * <b>Immutable</b>
 */
public class Sprite implements ISprite {
    private final String name;
    private final Image image;
    private final int width, height;
    private final double ratio;

    /* package */ Sprite(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.ratio = (double) height / (double) width;
    }



    @Override
    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    @Override
    /** {@inheritDoc} */
    public Image getImage() {
        return image;
    }

    @Override
    /** {@inheritDoc} */
    public int getWidth() {
        return width;
    }

    @Override
    /** {@inheritDoc} */
    public int getHeight() {
        return height;
    }

    @Override
    /** {@inheritDoc} */
    public double getRatio() {
        return ratio;
    }
}
