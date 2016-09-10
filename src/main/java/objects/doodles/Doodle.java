package objects.doodles;

import com.sun.webkit.dom.KeyboardEventImpl;
import objects.GameObject;
import objects.IGameObject;
import system.Game;

import java.awt.*;

/**
 * Created by Nick on 7-9-2016.
 */
public class Doodle extends GameObject implements IDoodle {


    // Keep track if the doodle is still alive
    private boolean alive = true;
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

    public Doodle(){
        System.out.println("AAAAAAAA" + Game.width);
        setXPos(0.5 * Game.width);
        setYPos(0.5 * Game.height);
        setHeight(20);
        setWidth(20);
    }



    //TODO: change to use Graphics (swing?)
    @Override
    public void paint(Graphics g) {
        this.update();

        g.fillRect(
                (int) this.getXPos(),
                (int) this.getYPos(),
                20,
                20
        );
    }

    //TODO: change to support correct implementation
    public void animate() { }

    //TODO: change to support correct implementation
    public void update() {
        this.move();
    }

    /**
     * Move the Doodle.
     */
    public void move() {
        moveHorizontally();
        //moveVertically();
    }

    /**
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally() {
        boolean keyLeft = false;
        boolean keyRight = false;

        if(keyLeft && this.hAcceleration > -this.hAccelerationLimit) {
            // Go left
            this.hAcceleration -= this.hAccelerationUnit;
        } else if(keyRight && this.hAcceleration < this.hAccelerationLimit) {
            // Go right
            this.hAcceleration += this.hAccelerationUnit;
        }

        this.addXPos(this.hAcceleration);
    }

    /**
     * Move the Doodle along the Y axis.
     */
    private void moveVertically() {
        // Check for collisions if the doodle is falling
        if(this.vAcceleration > 0) {
            this.collided();
        }

        // Apply gravity to the doodle
        this.applyGravity();

        // Apply the vAcceleration to the doodle
        double screenHeight = Game.height;
        if(this.vAcceleration < 0 && this.getYPos() > (1/2d) * screenHeight) {
            // Move background
            // background.addYPos(this.vAcceleration);
        } else {
            // Move doodle
            this.addYPos(this.vAcceleration);
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
    private void collided() {
        /*
        for(GameObject in Block) {
            if(this.collide(that) {
                this.vAcceleration = this.vAccelerationLimit + this.gravityAcceleration;
            }
        }
        */
    }

    public boolean isAlive() {
        return alive;
    }

    public float getVAccel(){return vAcceleration;}

    public void setVAccel (float vAccel){
        vAcceleration = vAccel;
    }
}
