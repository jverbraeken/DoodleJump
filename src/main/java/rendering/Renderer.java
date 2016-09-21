package rendering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.Graphics;

/**
 * This class is responsible for rendering all Sprites.
 */
public final class Renderer implements IRenderer {

    /**
     * Used to log all actions of the game.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Renderer.class);
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The graphics that are to be used by the renderer.
     */
    private Graphics graphics;

    /**
     * Prevent instantiations of the Renderer.
     */
    private Renderer() {
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
    public void start() {
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

        LOGGER.info("drawSprite(" + sprite.getName() + ", " + x + ", " + y + ")");
        graphics.drawImage(sprite.getImage(), x, y, null);
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

        LOGGER.info("drawSprite(" + sprite.getName() + ", " + x + ", " + y + ", " + width + ", " + height + ")");
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

}
