package objects.doodles;

import com.sun.webkit.dom.KeyboardEventImpl;
import objects.GameObject;

/**
 * Created by Nick on 7-9-2016.
 */
public class Doodle extends GameObject implements IDoodle {

    // The vertical acceleration of the Doodle, positive if going up and negative if going down.
    private float vAcceleration = 0f;
    // The fastest the doodle can go vertically.
    private float vAccelerationLimit = 6f;
    // How much the doodle is affected by gravity.
    private float gravityAcceleration = .25f;
    // The horizontal speed of the Doodle.
    private float hSpeed = .25f;

    //TODO: change to use Graphics (swing?)
    public void paint() { }

    //TODO: change to support correct implementation
    public void animate() { }

    //TODO: change to support correct implementation
    public void update() { }

    /**
     * Move the Doodle.
     */
    public void move() {
        // Check for collisions if the doodle is falling
        if(this.vAcceleration < 0) {
            this.collided();
        }

        // Apply gravity to the doodle
        this.applyGravity();

        // Apply the vAcceleration to the doodle
        double screenHeight = 500;
        if(this.vAcceleration > 0 && this.getYPos() > (2/3d) * screenHeight) {
            // Move background
            // background.addYPos(this.vAcceleration);
        } else {
            // Move doodle
            this.addYPos(this.vAcceleration);
        }

        // Move player for user
        boolean keyleft = false;
        boolean keyright = false;
        if(keyleft) {
            // Go left
            this.addXPos(-this.hSpeed);
        } else if(keyright) {
            // Go right
            this.addXPos(this.hSpeed);
        }
    }
    /**
     * Applies gravity vAcceleration to the doodle.
     */
    private void applyGravity() {
        if(this.vAcceleration >= -vAccelerationLimit) {
            this.vAcceleration -= this.gravityAcceleration;
        }
    }

    /**
     * Find out whether or not the Doodle collided with something.
     */
    private void collided() {
        /*
        for(GameObject in Block) {
            if(this.collide(that) {
                this.vAcceleration = this.vAccelerationLimit + this.gravityAcceleration;
            }
        }
        */

        this.vAcceleration = this.vAccelerationLimit + this.gravityAcceleration;
    }

}
