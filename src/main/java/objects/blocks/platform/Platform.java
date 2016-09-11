package objects.blocks.platform;

import objects.AGameObject;
import objects.Collisions;
import objects.IGameObject;
import objects.doodles.IDoodle;
import system.IServiceLocator;

import java.awt.*;

public class Platform extends AGameObject implements IPlatform {

    private static IServiceLocator serviceLocator;

    /**
     * Create and initiate a Platform object
     * @param x - the X position of the platform
     * @param y - the y position of the platform
     */
    public Platform(IServiceLocator serviceLocator, int x, int y) {
        super();

        Platform.serviceLocator = serviceLocator;

        this.setXPos(x);
        this.setYPos(y);
    }

    @Override
    public void animate() {

    }

    @Override
    public void move() {

    }

    @Override
    /** {@inheritDoc} */
    public void paint() {
        serviceLocator.getRenderer().drawRectangle(this.getXPos(), this.getYPos(), 50, 20);
    }

    @Override
    public void update() {

    }

}
