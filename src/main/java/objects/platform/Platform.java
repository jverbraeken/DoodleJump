package objects.platform;

import objects.GameObject;
import system.IServiceLocator;

import java.awt.*;

public class Platform extends GameObject implements IPlatform {

    private static IServiceLocator serviceLocator;

    private int xPos;
    private int yPos;

    /**
     * Create and initiate the Platform object
     * @param x - the X position of the platform
     * @param y - the y position of the platform
     */
    public Platform(IServiceLocator serviceLocator, int x, int y) {
        super();
        Platform.serviceLocator = serviceLocator;
        xPos = x;
        yPos = y;
    }

    @Override
    /** {@inheritDoc} */
    public void paint() {
        serviceLocator.getRenderer().drawRectangle(xPos, yPos, 50, 20);
    }

    //TODO: change to support correct implementation
    @Override
    /** {@inheritDoc} */
    public void animate() { }

    //TODO: change to support correct implementation
    @Override
    /** {@inheritDoc} */
    public void move() {  }

    //TODO: change to support correct implementation
    @Override
    /** {@inheritDoc} */
    public void update() { }

}
