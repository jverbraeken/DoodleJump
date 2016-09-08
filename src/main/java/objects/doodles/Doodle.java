package objects.doodles;

import objects.GameObject;

/**
 * Created by Nick on 7-9-2016.
 */
public class Doodle extends GameObject implements IDoodle {

    /**
     * The acceleration of the Doodle, positive if going up
     * and negative if going done.
     */
    public float acceleration = 0f;

    //TODO: change to use Graphics (swing?)
    public void paint() {

    }

    //TODO: change to support correct implementation
    public void animate() {

    }

    //TODO: change to support correct implementation
    public void move() {
        this.applyGravity();
    }

    //TODO: change to support correct implementation
    public void update() {

    }

    /**
     * Applies gravity acceleration to the doodle.
     */
    private void applyGravity() {
        if(this.acceleration >= -6) {
            this.acceleration -= .25f;
        }
    }

}
