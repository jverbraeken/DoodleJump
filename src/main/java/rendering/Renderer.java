package rendering;

import system.IServiceLocator;

import java.awt.*;

public final class Renderer implements IRenderer {

    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;

    private Graphics graphics;

    /**
    * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
    * @param serviceLocator The IServiceLocator to which the class should offer its functionality
    */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Renderer.serviceLocator = serviceLocator;
        serviceLocator.provide(new Renderer());
    }

    private Renderer() { }

    @Override
    /** {@inheritDoc} */
    public void start() { }

    public void drawRectangle(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    @Override
    /** {@inheritDoc} */
    public void drawImage(Image image, int x, int y) {
        assert graphics != null;
        if (image == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }
        graphics.drawImage(image, x, y, null);
    }

    @Override
    /** {@inheritDoc} */
    public void drawImage(Image image, int x, int y, int width, int height) {
        graphics.drawImage(image, x, y, width, height, null);
    }

    @Override
    /** {@inheritDoc} */
    public void setGraphicsBuffer(Graphics graphics) {
        if (graphics == null) {
            throw new IllegalArgumentException("The graphics buffer cannot be null");
        }
        this.graphics = graphics;
    }

}
