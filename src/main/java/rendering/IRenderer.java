package rendering;

import resources.sprites.ISprite;

import java.awt.*;

/**
 * This class is responsible for rendering all Sprites.
 */
public interface IRenderer {

    /**
     * Clear the game screen.
     */
    void clear();

    /**
     * Draw a sprite relative to the camera.
     *
     * @param image the sprite to be drawn.
     * @param point     the position of the sprite.
     */
    void drawSprite(final ISprite image, final Point point);

    /**
     * Draw a sprite relative to the camera.
     *
     * @param image  the sprite to be drawn.
     * @param point      the position of the sprite.
     * @param theta  the angle to rotate the sprite by.
     */
    void drawSprite(final ISprite image, final Point point, final double theta);

    /**
     * Draw a sprite relative to the camera.
     *
     * @param image  the sprite to be drawn.
     * @param point  the position of the sprite.
     * @param width  the width of the sprite.
     * @param height the height of the sprite.
     */
    void drawSprite(final ISprite image, final Point point, final int width, final int height);

    /**
     * Draw a sprite relative to the camera.
     *
     * @param image  the sprite to be drawn.
     * @param point  the position of the sprite.
     * @param width  the width of the sprite.
     * @param height the height of the sprite.
     * @param theta  the angle to rotate the sprite by.
     */
    void drawSprite(final ISprite image, final Point point, final int width, final int height, final double theta);

    /**
     * Draw a rectangle relative to the camera.
     *
     * @param point  the position of the rectangle
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    void drawRectangle(final Point point, final int width, final int height);

    /**
     * Draw a sprite relative to the screen.
     *
     * @param image the sprite to be drawn.
     * @param point the position of the sprite.
     */
    void drawSpriteHUD(final ISprite image, final Point point);

    /**
     * Draw a sprite relative to the screen.
     *
     * @param image  the sprite to be drawn.
     * @param point  the position of the sprite.
     * @param width  the width of the sprite.
     * @param height the height of the sprite.
     */
    void drawSpriteHUD(final ISprite image, final Point point, final int width, final int height);

    /**
     * Draw a rectangle relative to the screen.
     *
     * @param point  the position of the rectangle
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    void drawRectangleHUD(final Point point, final int width, final int height);

    /**
     * Draw a string of text relative to the camera, left-aligned.
     *
     * @param point
     * @param msg The message to draw.
     */
    void drawText(Point point, final String msg);

    /**
     * Draw a string of text relative to the screen, left-aligned.
     *
     * @param point     The position of the text.
     * @param msg       The message to draw.
     */
    void drawTextHUD(final Point point, final String msg);

    /**
     * Draw a string of text relative to the screen.
     *
     * @param point     The position of the text.
     * @param msg       The message to draw.
     * @param alignment The alignment of the text.
     */
    void drawText(final Point point, String msg, TextAlignment alignment);

    /**
     * Draw a string of text relative to the screen.
     *
     * @param point     The position of the text.
     * @param msg       The message to draw.
     * @param alignment The alignment of the text.
     */
    void drawTextHUD(final Point point, String msg, TextAlignment alignment);

    /**
     * Draw a string of text relative to the camera, left-aligned.
     *
     * @param point     The position of the text.
     * @param msg       The message to draw.
     */
    void drawText(final Point point, final String msg, final Color color);

    /**
     * Draw a string of text relative to the screen, left-aligned.
     *
     * @param point     The position of the text.
     * @param msg       The message to draw.
     */
    void drawTextHUD(final Point point, final String msg, final Color color);

    /**
     * Draw a string of text relative to the screen.
     *
     * @param point     the position of the text.
     * @param msg       The message to draw.
     * @param alignment The alignment of the text.
     */
    void drawText(final Point point, String msg, TextAlignment alignment, final Color color);

    /**
     * Draw a string of text relative to the screen.
     *
     * @param point     The position of the text.
     * @param msg       The message to draw.
     * @param alignment The alignment of the text.
     */
    void drawTextHUD(final Point point, String msg, TextAlignment alignment, final Color color);

    /**
     * Draw a filled rectangle.
     *
     * @param point  the position of the rectangle.
     * @param width  the width of the rectangle.
     * @param height the height of the rectangle.
     * @param color  the color of the rectangle.
     */
    void fillRectangle(final Point point, final int width, final int height, final Color color);

    /**
     * Create a graphics buffer for smooth animations and rendering.
     *
     * @param graphics the input graphics.
     */
    void setGraphicsBuffer(final Graphics graphics);

    /**
     * Get the camera used by the Renderer.
     *
     * @return The camera the Renderer is using
     */
    ICamera getCamera();

    /**
     * Set the camera used by the Renderer.
     *
     * @param camera A class implementing the ICamera interface.
     */
    void setCamera(final ICamera camera);

}
