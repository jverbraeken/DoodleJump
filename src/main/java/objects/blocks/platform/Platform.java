package objects.blocks.platform;

import objects.AGameObject;
import system.IServiceLocator;

import java.awt.*;

public class Platform extends AGameObject implements IPlatform {

    private static IServiceLocator serviceLocator;

    private int xPos;
    private int yPos;

    /**
     * Create and initiate a Platform object
     * @param x - the X position of the platform
     * @param y - the y position of the platform
     */
    public Platform(final IServiceLocator serviceLocator, final int x, final int y) {
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

    @Override
    public boolean collide(AGameObject that) {
        return false;
    }

    @Override
    public int getXPos() {
        return 0;
    }

    @Override
    public int getYPos() {
        return 0;
    }

    @Override
    public void setXPos(int xPos) {

    }

    @Override
    public void setYPos(int yPos) {

    }

    @Override
    public double[] getHitBox() {
        return new double[0];
    }

    @Override
    public Object getSprite() {
        return null;
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
