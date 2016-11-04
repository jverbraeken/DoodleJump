package rendering;

import constants.IConstants;
import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.Point;

/**
 * This class is responsible for rendering all Sprites.
 */
public final class Renderer implements IRenderer {

    /**
     * The font size used for {@link #font50}.
     */
    private static final float font50SIZE = 50F;

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
        font50 = font.deriveFont(Font.BOLD, font50SIZE);
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
    public void drawRectangle(final Point point, final int width, final int height) {
        assert this.graphics != null;

        String drawMsg = "drawRectangle(" + point.getX() + ", " + point.getY() + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (point.getY() - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        this.graphics.drawRect((int) point.getX(), (int) (point.getY() - this.camera.getYPos()), width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(final ISprite sprite, final Point point) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        String drawMsg = "drawSprite(" + sprite.getName() + ", " + point.getX() + ", " + point.getY() + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (point.getY() - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        this.graphics.drawImage(sprite.getImage(), (int) point.getX(), (int) (point.getY() - this.camera.getYPos()), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(final ISprite sprite, final Point point, final double theta) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        String drawMsg = "drawSprite(" + sprite.getName() + ", " + point.getX() + ", " + point.getY() + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (point.getY() - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        double halfWidth = (double) sprite.getWidth() / 2d;
        double halfHeight = (double) sprite.getHeight() / 2d;
        double translateX = point.getX() + halfWidth;
        double translateY = point.getY() - this.camera.getYPos() + halfHeight;

        this.graphics.translate(translateX, translateY);
        this.graphics.rotate(theta);
        this.graphics.translate(-halfWidth, -halfHeight);
        this.graphics.drawImage(sprite.getImage(), 0, 0, null);
        this.graphics.translate(halfWidth, halfHeight);
        this.graphics.rotate(-theta);
        this.graphics.translate(-translateX, -translateY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(final ISprite sprite, final Point point, final int width, final int height) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        String drawMsg = "drawSprite(" + sprite.getName() + ", " + point.getX() + ", " + point.getY() + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (point.getY() - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        this.graphics.drawImage(sprite.getImage(), (int) point.getX(), (int) (point.getY() - this.camera.getYPos()), width, height, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSprite(final ISprite sprite, final Point point, final int width, final int height, final double theta) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        String drawMsg = "drawSprite(" + sprite.getName() + ", " + point.getX() + ", " + point.getY() + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (point.getY() - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        double halfWidth = (double) sprite.getWidth() / 2d;
        double halfHeight = (double) sprite.getHeight() / 2d;
        double translateX = point.getX() + halfWidth;
        double translateY = point.getY() - this.camera.getYPos() + halfHeight;

        this.graphics.translate(translateX, translateY);
        this.graphics.rotate(theta);
        this.graphics.translate(-halfWidth, -halfHeight);
        this.graphics.drawImage(sprite.getImage(), 0, 0, width, height, null);
        this.graphics.translate(halfWidth, halfHeight);
        this.graphics.rotate(-theta);
        this.graphics.translate(-translateX, -translateY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawRectangleHUD(final Point point, final int width, final int height) {
        assert this.graphics != null;

        this.logger.info("drawRectangle(" + point.getX() + ", " + point.getY() + ", " + width + ", " + height + ")");
        this.graphics.drawRect((int) point.getX(), (int) point.getY(), width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSpriteHUD(final ISprite sprite, final Point point) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        this.logger.info("drawImage(" + point.getX() + ", " + point.getY() + ")");
        this.graphics.drawImage(sprite.getImage(), (int) point.getX(), (int) point.getY(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawSpriteHUD(final ISprite sprite, final Point point, final int width, final int height) {
        assert this.graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        this.logger.info("drawSprite(" + point.getX() + ", " + point.getY() + ")");
        this.graphics.drawImage(sprite.getImage(), (int) point.getX(), (int) point.getY(), width, height, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final Point point, final String msg) {
        assert this.graphics != null;
        drawText(point, msg, TextAlignment.left);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextHUD(final Point point, final String msg) {
        assert this.graphics != null;
        drawTextHUD(point, msg, TextAlignment.left);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final Point point, final String msg, final TextAlignment alignment) {
        assert graphics != null;
        drawText(new Point((int) point.getX(), (int) (point.getY() - camera.getYPos())), msg, alignment, Color.white);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextHUD(final Point point, final String msg, final TextAlignment alignment) {
        assert graphics != null;
        drawTextHUD(point,  msg, alignment, Color.white);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final Point point, final String msg, final Color color) {
        assert this.graphics != null;
        drawText(point, msg, TextAlignment.left, color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextExtraOptions(final Point point, final String msg, final Color color, final double rotation, final int fontSize) {
        assert this.graphics != null;
        java.awt.Color currentColor = graphics.getColor();
        int xPos = prepareDrawText(point, msg, TextAlignment.center, color.getColor(), serviceLocator.getFileSystem().getFont("al-seana.ttf").deriveFont(Font.BOLD, fontSize));
        graphics.rotate(rotation, xPos, point.getY());
        graphics.drawString(msg, xPos, (int) point.getY());
        this.logger.info("drawString(" + point.getX() + ", " + point.getY() + ", " + msg + ", " + color.name());

        graphics.setColor(currentColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextHUD(final Point point, final String msg, final Color color) {
        assert this.graphics != null;
        drawTextHUD(point, msg, TextAlignment.left, color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(final Point point, final String msg, final TextAlignment alignment, final Color color) {
        assert graphics != null;
        java.awt.Color currentColor = graphics.getColor();

        int xPos = prepareDrawText(point, msg, alignment, color.getColor(), font50);
        graphics.drawString(msg, xPos, (int) (point.getY() - camera.getYPos()));
        this.logger.info("drawString(" + point.getX() + ", " + point.getY() + ", " + msg + ", " + alignment.name() + ", " + color.name());

        graphics.setColor(currentColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextHUD(final Point point, final String msg, final TextAlignment alignment, final Color color) {
        assert graphics != null;
        java.awt.Color currentColor = graphics.getColor();

        int xPos = prepareDrawText(point, msg, alignment, color.getColor(), font50);
        graphics.drawString(msg, xPos, (int) point.getY());
        this.logger.info("drawString(" + point.getX() + ", " + point.getY() + ", " + msg + ", " + alignment.name() + ", " + color.name());

        graphics.setColor(currentColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillRectangle(final Point point, final int width, final int height, final Color color) {
        assert this.graphics != null;

        String drawMsg = "drawRectangle(" + point.getX() + ", " + point.getY() + ", " + width + ", " + height + ") - ";
        String cameraMsg = "Camera corrected Y-position = " + (point.getY() - this.camera.getYPos());
        this.logger.info(drawMsg + cameraMsg);

        java.awt.Color currentColor = graphics.getColor();
        graphics.setColor(color.getColor());
        graphics.fillRect((int) point.getX(), (int) (point.getY() - camera.getYPos()), width, height);
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
     * @param point The point at which the text should be drawn
     * @param msg The text that should be drawn
     * @param alignment The way the text is aligned
     * @param font The font that's used to draw the text
     * @return The X-position at which the text should be drawn
     */
    private int prepareDrawText(final Point point, final String msg, final TextAlignment alignment, final java.awt.Color color, final Font font) {
        graphics.setFont(font);
        graphics.setColor(color);
        double xPos;
        switch (alignment) {
            case left:
                xPos = point.getX();
                break;
            case center:
                xPos = point.getX() - (double) graphics.getFontMetrics().stringWidth(msg) / 2d;
                break;
            case right:
                xPos = point.getX() - (double) graphics.getFontMetrics().stringWidth(msg);
                break;
            default:
                final String error = "The text alignment enum constant could not be identified: " + alignment.toString();
                logger.error(error);
                throw new InternalError(error);
        }
        return (int) xPos;
    }

}
