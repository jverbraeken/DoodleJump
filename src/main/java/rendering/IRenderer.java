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
     * Draw a sprite relative to the camera.
     *
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     */
    void drawSprite(final ISprite image, int x, final int y);

    /**
     * Draw a sprite relative to the camera.
     *
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     * @param width the width of the sprite.
     * @param height the height of the sprite.
     */
    void drawSprite(final ISprite image, final int x, final int y, final int width, final int height);

    /**
     * Draw a rectangle relative to the camera.
     *
     * @param x the x position of the rectangle
     * @param y the y position of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    void drawRectangle(final int x, final int y, final int width, final int height);

    /**
     * Draw a sprite relative to the screen.
     *
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     */
    void drawSpriteHUD(final ISprite image, final int x, final int y);

    /**
     * Draw a sprite relative to the screen.
     *
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     * @param width the width of the sprite.
     * @param height the height of the sprite.
     */
    void drawSpriteHUD(final ISprite image, final int x, final int y, final int width, final int height);

    /**
     * Draw a rectangle relative to the screen.
     *
     * @param x the x position of the rectangle
     * @param y the y position of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    void drawRectangleHUD(final int x, final int y, final int width, final int height);

    /**
     * Create a graphics buffer for smooth animations and rendering.
     *
     * @param graphics the input graphics.
     */
    void setGraphicsBuffer(final Graphics graphics);

    /**
     * Get the camera used by the Renderer.
     *
     * @return The camera the Renderer is using.
     */
    ICamera getCamera();

}
