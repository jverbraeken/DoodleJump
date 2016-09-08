package objects.doodles;

import objects.GameObject;

/**
 * Created by Nick on 7-9-2016.
 */
public class Doodle extends GameObject implements IDoodle {

    // The acceleration of the Doodle, positive if going up and negative if going down.
    private float acceleration = 0f;
    // The fastest the doodle can go.
    private float accelerationLimit = 6f;
    // How much the doodle is affected by gravity.
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
        // Check for collisions if the doodle is falling
        if(this.acceleration < 0) {
            this.collided();
        }

        // Apply gravity to the doodle
        this.applyGravity();

        // Apply the acceleration to the doodle
        System.out.println(this.getYPos());
        double screenHeight = 500;
        if(this.getYPos() > (2/3d) * screenHeight) {
            // Move background
            // background.addYPos(this.acceleration);
        } else {
            // Move doodle
            this.addYPos(this.acceleration);
        }
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
