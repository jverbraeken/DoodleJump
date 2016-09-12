package rendering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.IScene;
import system.Game;
import system.IServiceLocator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public final class Renderer implements IRenderer {

    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;

    /**
    * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
    * @param serviceLocator The IServiceLocator to which the class should offer its functionality
    */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Renderer.serviceLocator = serviceLocator;
        serviceLocator.provide(new Renderer());
    }

    private Graphics graphics;
    private static final Logger logger = LoggerFactory.getLogger(Renderer.class);

    private Renderer() {
    }

    @Override
    /** {@inheritDoc} */
    public void start() {

    }

    public void drawRectangle(int x, int y, int width, int height) {
        logger.info("drawRectangle(" + x + ", y" + ", " + width + ", " + height + ")");
        graphics.drawRect(x, y, width, height);
    }

    @Override
    /** {@inheritDoc} */
    public void drawSprite(ISprite sprite, int x, int y) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }
        logger.info("drawSprite(" + sprite.getName() + ", " + x + ", " + y + ")");
        graphics.drawImage(sprite.getImage(), x, y, null);
    }

    @Override
    /** {@inheritDoc} */
    public void drawSprite(ISprite sprite, int x, int y, int width, int height) {
        assert graphics != null;
        if (sprite == null) {
            throw new IllegalArgumentException("A null image is not allowed");
        }
        logger.info("drawSprite(" + sprite.getName() + ", " + x + ", " + y + ", " + width + ", " + height + ")");
        graphics.drawImage(sprite.getImage(), x, y, width, height, null);
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
