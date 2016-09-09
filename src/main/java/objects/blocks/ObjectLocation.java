package objects.blocks;

import objects.GameObject;

/**
 * Created by cornel on 7-9-2016.
 * A ObjectLocation is an object that stores a x and y location and
 * an object. It also has getter methods for these variables.
 */
public class ObjectLocation {
    private GameObject object;
    private int x;
    private int y;

    /**
     * Create an ObjectLocation object.
     * @param x = the x location
     * @param y = the y location
     * @param object = the object on the certain location
     */
    public ObjectLocation(int x, int y, GameObject object){
        this.x = x;
        this.y = y;
        this.object = object;
    }

    /**
     * Gets the X coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the X coordinate
     * @return the x coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the object
     * @return the object
     */
    public GameObject getObject() {
        return object;
    }
}
