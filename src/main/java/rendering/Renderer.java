package rendering;

import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Graphics;


/**
 * This class is responsible for rendering all Sprites.
 */
public final class Renderer implements IRenderer {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Used to log all actions of the game.
     */
    private final ILogger logger;
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
        logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Renderer.serviceLocator = sL;
        sL.provide(new Renderer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawRectangle(final int x, final int y, final int width, final int height) {
        assert graphics != null;

        String drawMsg = "drawRectangle(" + x + ", y" + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (y - camera.getYPos());
        logger.info(drawMsg + cameraMsg);

        graphics.drawRect(x, (int) (y - camera.getYPos()), width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(final ISprite sprite, final int x, final int y) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        String drawMsg = "drawSprite(" + sprite.getName() + ", " + x + ", " + y + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (y - camera.getYPos());
        logger.info(drawMsg + cameraMsg);

        graphics.drawImage(sprite.getImage(), x, (int) (y - camera.getYPos()), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(final ISprite sprite, final int x, final int y, final int width, final int height) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        String drawMsg = "drawSprite(" + sprite.getName() + ", " + x + ", " + y + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (y - camera.getYPos());
        logger.info(drawMsg + cameraMsg);

        graphics.drawImage(sprite.getImage(), x, (int) (y - camera.getYPos()), width, height, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawRectangleHUD(final int x, final int y, final int width, final int height) {
        assert graphics != null;

        logger.info("drawRectangle(" + x + ", " + y + ", " + width + ", " + height + ")");

        graphics.drawRect(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSpriteHUD(final ISprite sprite, final int x, final int y) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        logger.info("drawImage(" + x + ", " + y + ")");

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

        logger.info("drawSprite(" + x + ", " + y + ")");

        graphics.drawImage(sprite.getImage(), x, y, width, height, null);
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
