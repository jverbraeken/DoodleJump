package resources.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;


@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
public class Sprite implements ISprite {
    private final String name;
    private final Image image;
    private final int width, height;
    private final double ratio;

    /* package */ Sprite(final String n, final BufferedImage i) {
        this.name = n;
        this.image = i;
        this.width = i.getWidth();
        this.height = i.getHeight();
        this.ratio = (double) height / (double) width;
    }

    @Override
    /** {@inheritDoc} */
    public final String getName() {
        return name;
    }

    @Override
    /** {@inheritDoc} */
    public final Image getImage() {
        return image;
    }

    @Override
    /** {@inheritDoc} */
    public final int getWidth() {
        return width;
    }

    @Override
    /** {@inheritDoc} */
    public  final int getHeight() {
        return height;
    }

    @Override
    /** {@inheritDoc} */
    public final double getRatio() {
        return ratio;
    }
}
