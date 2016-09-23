package rendering;

import resources.sprites.ISprite;

import java.awt.Graphics;

/**
 * This class is responsible for rendering all Sprites.
 */
public interface IRenderer {
    /**
     * Start the renderer.
     */
    void start();

    /**
     * Draw a sprite.
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     */
    void drawSprite(ISprite image, int x, int y);

    /**
     *
     * Draw a sprite.
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     * @param width the height of the sprite.
     * @param height the width of the sprite.
     */
    void drawSprite(ISprite image, int x, int y, int width, int height);

    /**
     * Create a graphics buffer for smooth animations and rendering.
     * @param graphics the input graphics.
     */
    void setGraphicsBuffer(Graphics graphics);
}
