package rendering;

import constants.IConstants;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * This class is responsible for rendering all Sprites.
 */
public final class Renderer implements IRenderer {

    /**
     * The default font size used by the Renderer.
     */
    private static final int FONT_SIZE = 28;

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
    private ICamera camera = new StaticCamera();
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
        Renderer.serviceLocator.provide(new Renderer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        IConstants constants = Renderer.serviceLocator.getConstants();
        this.graphics.clearRect(0, 0, constants.getGameWidth(), constants.getGameHeight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawRectangle(final int x, final int y, final int width, final int height) {
        assert this.graphics != null;

        String drawMsg = "drawRectangle(" + x + ", y" + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (y - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        this.graphics.drawRect(x, (int) (y - this.camera.getYPos()), width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(final ISprite sprite, final int x, final int y) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        String drawMsg = "drawSprite(" + sprite.getName() + ", " + x + ", " + y + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (y - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        this.graphics.drawImage(sprite.getImage(), x, (int) (y - this.camera.getYPos()), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(final ISprite sprite, final int x, final int y, final int width, final int height) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        String drawMsg = "drawSprite(" + sprite.getName() + ", " + x + ", " + y + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (y - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        this.graphics.drawImage(sprite.getImage(), x, (int) (y - this.camera.getYPos()), width, height, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawRectangleHUD(final int x, final int y, final int width, final int height) {
        assert this.graphics != null;

        this.logger.info("drawRectangle(" + x + ", " + y + ", " + width + ", " + height + ")");
        this.graphics.drawRect(x, y, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSpriteHUD(final ISprite sprite, final int x, final int y) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        this.logger.info("drawImage(" + x + ", " + y + ")");
        this.graphics.drawImage(sprite.getImage(), x, y, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSpriteHUD(final ISprite sprite, final int x, final int y, final int width, final int height) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        this.logger.info("drawSprite(" + x + ", " + y + ")");
        this.graphics.drawImage(sprite.getImage(), x, y, width, height, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final int x, final int y, final String msg) {
        assert this.graphics != null;

        this.logger.info("drawString(" + msg + ", " + x + ", " + y + ")");
        this.graphics.drawString(msg, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillRectangle(final int x, final int y, final int width, final int height, final Color color) {
        assert this.graphics != null;

        String drawMsg = "drawRectangle(" + x + ", y" + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (y - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        Color currentColor = this.graphics.getColor();
        this.graphics.setColor(color);
        this.graphics.fillRect(x, (int) (y - this.camera.getYPos()), width, height);
        this.graphics.setColor(currentColor);
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
        this.graphics.setFont(new Font("Comic Sans", 0, Renderer.FONT_SIZE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICamera getCamera() {
        return this.camera;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCamera(final ICamera c) {
        this.camera = c;
    }

}
