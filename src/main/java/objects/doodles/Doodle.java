package objects.doodles;

import objects.AGameObject;
import objects.blocks.platform.IPlatform;

public class Doodle extends AGameObject implements IDoodle {

    // The vertical acceleration of the Doodle, negative if going up and positive if going down.
    private float vAcceleration = 0f;
    // The fastest the doodle can go vertically.
    private float vAccelerationLimit = 6f;
    // How much the doodle is affected by gravity.
    private float gravityAcceleration = .15f;
    // The horizontal acceleration of the Doodle, positive if going right and negative if going left.
    private float hAcceleration = 0f;
    // The fastest the doodle can go horizontally.
    private float hAccelerationLimit = 3f;
    // How much the doodle is affected by the player.
    private float hAccelerationUnit = .15f;

    /* package */ Doodle() { }

    //TODO: change to use Graphics (swing?)
    @Override
    public void paint() { }

    //TODO: change to support correct implementation
    public void animate() { }

    //TODO: change to support correct implementation
    public void move() {
        this.moveHorizontally();
        this.moveVertically();
    }

    //TODO: change to support correct implementation
    public void update() { }


    /**
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally() {
        boolean keyLeft = false;
        boolean keyRight = true;

        if(keyLeft && this.hAcceleration > -this.hAccelerationLimit) {
            // Go left
            this.hAcceleration -= this.hAccelerationUnit;
        } else if(keyRight && this.hAcceleration < this.hAccelerationLimit) {
            // Go right
            this.hAcceleration += this.hAccelerationUnit;
        }

        this.addXPos((int) this.hAcceleration);
    }

    /**
     * Move the Doodle along the Y axis.
     */
    private void moveVertically() {
        // Apply gravity to the doodle
        this.applyGravity();

        // Apply the vAcceleration to the doodle
        double screenHeight = 500;
        if(this.vAcceleration > 0 && this.getYPos() > .5d * screenHeight) {
            // Move background
            // background.addYPos(this.vAcceleration);
        } else {
            // Move doodle
            this.addYPos((int) this.vAcceleration);
        }
    }

    /**
     * Applies gravity vAcceleration to the doodle.
     */
    private void applyGravity() {
        if(this.vAcceleration >= -vAccelerationLimit) {
            this.vAcceleration += this.gravityAcceleration;
        }
    }

    /**
     * Find out whether or not the Doodle collided with something.
     */
    public void collide(IPlatform platform) {
        System.out.println("Collide doodle with platform");
    }

}
