package resources.sprites;

import java.awt.Image;

/**
 * Interface for a sprite.
 */
public interface ISprite {

    /**
     * Get the name of the sprite.
     *
     * @return The name of the sprite.
     */
    String getName();

    /**
     * Get the image of the sprite.
     *
     * @return The image of the sprite.
     */
    Image getImage();

    /**
     * Get the width of the sprite.
     *
     * @return The width of the sprite.
     */
    int getWidth();

    /**
     * Get the height of the sprite.
     *
     * @return The height of the sprite.
     */
    int getHeight();

    /**
     * Get the ratio of the sprite.
     *
     * @return The ratio of the sprite.
     */
    double getRatio();

}
