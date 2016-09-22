package rendering;

import logging.ILogger;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

public final class Renderer implements IRenderer {


    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Renderer.serviceLocator = serviceLocator;
        serviceLocator.provide(new Renderer());
    }
    /**
     * The logger for the Renderer class.
     */
    private static ILogger LOGGER;
    private Graphics graphics;

    private Renderer() {
        Renderer.LOGGER = serviceLocator.getLoggerFactory().createLogger(Renderer.class);
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
    }

    public void drawRectangle(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public void drawSprite(ISprite sprite, int x, int y) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        graphics.drawImage(sprite.getImage(), x, y, null);
    }

    /** {@inheritDoc} */
    @Override
    public void drawSprite(ISprite sprite, int x, int y, int width, int height) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        graphics.drawImage(sprite.getImage(), x, y, width, height, null);
    }
    /** {@inheritDoc} */

    @Override
    public void setGraphicsBuffer(Graphics graphics) {
        if (graphics == null) {
            throw new IllegalArgumentException("The graphics buffer cannot be null");
        }
        this.graphics = graphics;
    }

}
