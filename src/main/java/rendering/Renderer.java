package rendering;

import constants.IConstants;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;

/**
 * This class is responsible for rendering all Sprites.
 */
public final class Renderer implements IRenderer {

    /**
     * The font size used for {@link #font50}.
     */
    private static final float FONT50SIZE = 50F;

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
    private Graphics2D graphics;
    /**
     * The font used to draw text with size 50.
     */
    private final Font font50;

    /**
     * Prevent public instantiations of the Renderer.
     */
    private Renderer() {
        logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
        Font font = serviceLocator.getFileSystem().getFont("al-seana.ttf");
        font50 = font.deriveFont(Font.BOLD, FONT50SIZE);
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
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
        drawText(x, y, msg, TextAlignment.left);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextHUD(final int x, final int y, final String msg) {
        assert this.graphics != null;
        drawTextHUD(x, y, msg, TextAlignment.left);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final int x, final int y, final String msg, final TextAlignment alignment) {
        assert graphics != null;
        drawText(x, (int) (y - camera.getYPos()), msg, alignment, Color.white);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextHUD(final int x, final int y, final String msg, final TextAlignment alignment) {
        assert graphics != null;
        drawTextHUD(x, y, msg, alignment, Color.white);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final int x, final int y, final String msg, final Color color) {
        assert this.graphics != null;
        drawText(x, y, msg, TextAlignment.left, color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextHUD(final int x, final int y, final String msg, final Color color) {
        assert this.graphics != null;
        drawTextHUD(x, y, msg, TextAlignment.left, color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final int x, final int y, final String msg, final TextAlignment alignment, final Color color) {
        assert graphics != null;
        java.awt.Color currentColor = graphics.getColor();

        int xPos = prepareDrawText(x, msg, alignment, font50);
        graphics.drawString(msg, xPos, (int) (y - camera.getYPos()));
        this.logger.info("drawString(" + x + ", " + y + ", " + msg + ", " + alignment.name() + ", " + color.name());

        graphics.setColor(currentColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextHUD(final int x, final int y, final String msg, final TextAlignment alignment, final Color color) {
        assert graphics != null;
        java.awt.Color currentColor = graphics.getColor();

        int xPos = prepareDrawText(x, msg, alignment, font50);
        graphics.drawString(msg, xPos, y);
        this.logger.info("drawString(" + x + ", " + y + ", " + msg + ", " + alignment.name() + ", " + color.name());

        graphics.setColor(currentColor);
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

        java.awt.Color currentColor = graphics.getColor();
        graphics.setColor(color.getColor());
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

        this.graphics = (Graphics2D) g;

        this.graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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

    /**
     * Returns the X-position at which text should be drawn.
     *
     * @param x The X-position of the text as requested
     * @param msg The text that should be drawn
     * @param alignment The way the text is aligned
     * @param font The font that's used to draw the text
     * @return The X-position at which the text should be drawn.
     */
    private int prepareDrawText(final int x, final String msg, final TextAlignment alignment, final Font font) {
        graphics.setFont(font);
        int xPos = x;
        switch (alignment) {
            case left:
                xPos = x;
                break;
            case center:
                xPos = x - graphics.getFontMetrics().stringWidth(msg) / 2;
                break;
            case right:
                xPos = x - graphics.getFontMetrics().stringWidth(msg);
                break;
            default:
                final String error = "The text alignment enum constant could not be identified: " + alignment.toString();
                logger.error(error);
                throw new InternalError(error);
        }
        return xPos;
    }

}
