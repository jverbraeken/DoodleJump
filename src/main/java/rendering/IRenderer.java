package rendering;

import resources.sprites.ISprite;

import java.awt.*;

/**
 * This class is responsible for rendering all Sprites.
 */
public interface IRenderer {

    /**
     * Start the renderer.
     */
    void start();

    /**
     * Clear the game screen.
     */
    void clear();

    /**
     * Draw a rectangle relative to the camera.
     * @param x the x position of the rectangle
     * @param y the y position of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    void drawRectangle(int x, int y, int width, int height);

    /**
     * Draw a rectangle relative to the screen.
     * @param x the x position of the rectangle
     * @param y the y position of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    void drawRectangleHUD(int x, int y, int width, int height);

    /**
     * Draw a sprite relative to the camera.
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     */
    void drawSprite(ISprite image, int x, int y);

    /**
     * Draw a sprite relative to the camera.
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     * @param width the width of the sprite.
     * @param height the height of the sprite.
     */
    void drawSprite(ISprite image, int x, int y, int width, int height);

    /**
     * Draw a sprite relative to the screen.
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     */
    void drawSpriteHUD(ISprite image, int x, int y);

    /**
     * Draw a sprite relative to the screen.
     * @param image the sprite to be drawn.
     * @param x the x position of the sprite.
     * @param y the y position of the sprite.
     * @param width the width of the sprite.
     * @param height the height of the sprite.
     */
    void drawSpriteHUD(ISprite image, int x, int y, int width, int height);

    /**
     * Draw a string on the screen.
     * @param msg The string to draw.
     * @param x the x position of the string.
     * @param y the y position of the string.
     */
    void drawText(final String msg, final int x, final int y);

    /**
     * Draw a filled rectangle.
     * @param x the x position of the rectangle.
     * @param y the y position of the rectangle.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     * @param color the color of the rectangle.
     */
    void fillRectangle(int x, int y, int width, int height, Color color);

    /**
     * Create a graphics buffer for smooth animations and rendering.
     * @param graphics the input graphics.
     */
    void setGraphicsBuffer(Graphics graphics);

    /**
     * Get the camera used by the Renderer.
     * @return The camera the Renderer is using
     */
    ICamera getCamera();

}
