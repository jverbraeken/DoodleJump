package rendering;

import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

/**
 * This class is responsible for rendering all Sprites.
 */
public final class Renderer implements IRenderer {

    /**
     * Used to log all actions of the game.
     */
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Renderer.sL = sL;
        Renderer.sL.provide(new Renderer());
    }

    /**
     * The camera for the renderer.
     */
    private final ICamera camera = new Camera();

    /**
     * The graphics that are to be used by the renderer.
     */
    private Graphics graphics;

    /**
     * Prevent public instantiations of the Renderer.
     */
    private Renderer() {
        LOGGER = sL.getLoggerFactory().createLogger(this.getClass());
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawRectangle(int x, int y, int width, int height) {
        LOGGER.info("drawRectangle(" + x + ", y" + ", " + width + ", " + height + ") - Camera corrected Y-position = " + (y - camera.getYPos()));
        graphics.drawRect(x, (int) (y - camera.getYPos()), width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawRectangleHUD(int x, int y, int width, int height) {
        LOGGER.info("drawRectangle(" + x + ", y" + ", " + width + ", " + height + ")");
        graphics.drawRect(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(ISprite sprite, int x, int y) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        LOGGER.info("drawSprite(" + sprite.getName() + ", " + x + ", " + y + ") - Camera corrected Y-position = " + (y - camera.getYPos()));
        graphics.drawImage(sprite.getImage(), x, (int) (y - camera.getYPos()), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(ISprite sprite, int x, int y, int width, int height) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        LOGGER.info("drawSprite(" + sprite.getName() + ", " + x + ", " + y + ", " + width + ", " + height + ") - Camera corrected Y-position = " + (y - camera.getYPos()));
        graphics.drawImage(sprite.getImage(), x, (int) (y - camera.getYPos()), width, height, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSpriteHUD(ISprite sprite, int x, int y) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        graphics.drawImage(sprite.getImage(), x, y, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSpriteHUD(final ISprite sprite, final int x, final int y, final int width, final int height) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        graphics.drawImage(sprite.getImage(), x, y, width, height, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final String msg, final int x, final int y) {
        assert graphics != null;
        graphics.drawString(msg, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillRectangle(int x, int y, int width, int height, Color color) {
        assert graphics != null;
        LOGGER.info("drawRectangle(" + x + ", y" + ", " + width + ", " + height + ") - Camera corrected Y-position = " + (y - camera.getYPos()));

        Color currentColor = graphics.getColor();
        graphics.setColor(color);
        graphics.fillRect(x, (int) (y - camera.getYPos()), width, height);
        graphics.setColor(currentColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGraphicsBuffer(final Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException("The graphics buffer cannot be null");
        }

        this.graphics = g;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICamera getCamera() {
        return camera;
    }

}
