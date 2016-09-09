package objects.platform;

import objects.GameObject;

import java.awt.*;

/**
 * Created by Nick on 7-9-2016.
 */
public class Platform extends GameObject implements IPlatform {

    private int xPos;
    private int yPos;

    /**
     * Create and initiate the Platform object
     * @param x - the X position of the platform
     * @param y - the y position of the platform
     */
    public Platform(int x, int y) {
        super();
        xPos = x;
        yPos = y;
    }

    /** {@inheritDoc} */
    public void paint(Graphics g) {
        g.drawRect(xPos,yPos,50,20);
    }

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    public void animate() { }

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    public void move() {  }

    //TODO: change to support correct implementation
    /** {@inheritDoc} */
    public void update() { }

}
