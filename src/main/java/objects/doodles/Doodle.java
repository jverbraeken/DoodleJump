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
    private float accelerationLimit = 6f;
    private float gravityAcceleration = .25f;

    //TODO: change to use Graphics (swing?)
    public void paint() {

    }

    //TODO: change to support correct implementation
    public void animate() {

    }

    /**
     * Move the Doodle.
     */
    public void move() {
        if(this.acceleration < 0) {
            this.collided();
        }

        this.applyGravity();
    }

    //TODO: change to support correct implementation
    public void update() {

    }

    /**
     * Applies gravity acceleration to the doodle.
     */
    private void applyGravity() {
        if(this.acceleration >= -accelerationLimit) {
            this.acceleration -= this.gravityAcceleration;
        }
    }

    /**
     * Find out whether or not the Doodle collided with something.
     */
    private void collided() {
        /*
        for(GameObject in Block) {
            if(this.collide(that) {
                this.acceleration = this.accelerationLimit + this.gravityAcceleration;
            }
        }
         */

        this.acceleration = this.accelerationLimit + this.gravityAcceleration;
    }

}
