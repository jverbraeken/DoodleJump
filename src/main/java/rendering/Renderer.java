package rendering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.awt.*;

public final class Renderer implements IRenderer {

    private static final Logger logger = LoggerFactory.getLogger(Renderer.class);
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    private Graphics graphics;

    private final ICamera camera = new Camera();

    private Renderer() { }

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

    /** {@inheritDoc} */
    @Override
    public void start() {
    }

    public void drawRectangle(int x, int y, int width, int height) {
        logger.info("drawRectangle(" + x + ", y" + ", " + width + ", " + height + ") - Camera corrected Y-position = " + (y - camera.getYPos()));
        graphics.drawRect(x, (int) (y - camera.getYPos()), width, height);
    }

    /** {@inheritDoc} */
    @Override
    public void drawSprite(ISprite sprite, int x, int y) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        logger.info("drawSprite(" + sprite.getName() + ", " + x + ", " + y + ") - Camera corrected Y-position = " + (y - camera.getYPos()));
        graphics.drawImage(sprite.getImage(), x, (int) (y - camera.getYPos()), null);
    }

    /** {@inheritDoc} */
    @Override
    public void drawSprite(ISprite sprite, int x, int y, int width, int height) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }

        logger.info("drawSprite(" + sprite.getName() + ", " + x + ", " + y + ", " + width + ", " + height + ") - Camera corrected Y-position = " + (y - camera.getYPos()));
        graphics.drawImage(sprite.getImage(), x, (int) (y - camera.getYPos()), width, height, null);
    }

    /** {@inheritDoc} */
    @Override
    public void setGraphicsBuffer(Graphics graphics) {
        if (graphics == null) {
            throw new IllegalArgumentException("The graphics buffer cannot be null");
        }
        this.graphics = graphics;
    }


    /** {@inheritDoc} */
    @Override
    public ICamera getCamera() {
        return camera;
    }
}
