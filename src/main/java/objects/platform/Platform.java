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
     * Creat and initiate the Platform object
     * @param x - the X position of the platform
     * @param y - the y position of the platform
     */
    public Platform(int x, int y) {
        super();
        xPos = x;
        yPos = y;
    }

    //TODO: change to use Graphics (swing?)
    public void paint(Graphics g) {

    }

    //TODO: change to support correct implementation
    public void animate() {

    }

    //TODO: change to support correct implementation
    public void move() {

    }

    //TODO: change to support correct implementation
    public void update() {

    }
}
