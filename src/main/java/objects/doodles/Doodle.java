package objects.doodles;

import input.IInputManager;
import input.KeyCode;
import input.Keys;
import objects.AGameObject;
import objects.ICollisions;
import objects.IGameObject;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

public class Doodle extends AGameObject implements IDoodle {

    private static IServiceLocator serviceLocator;

    // The vertical acceleration of the Doodle, negative if going up and positive if going down.
    private float vAcceleration = 0f;
    // The fastest the doodle can go vertically.
    private float vAccelerationLimit = 9f;
    // How much the doodle is affected by gravity.
    private float gravityAcceleration = .15f;
    // How much the doodle is affected by the player.
    private float hSpeedUnit = 5;
    // The sprite for the doodle
    private ISprite sprite;

    private enum directions { left, right }

    /* package */ Doodle(IServiceLocator serviceLocator) {
        Doodle.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getDoodleSprite();

        IInputManager inputManager = serviceLocator.getInputManager();
        inputManager.addObserver(this);

        this.setXPos(Game.WIDTH / 2);
        this.setYPos(Game.HEIGHT / 2);
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
    }

    @Override
    public void animate() { }

    @Override
    public void collide(IGameObject collidee) {
        ICollisions collisions = serviceLocator.getCollisions();
        boolean collided = collisions.collide(this, collidee);

        if(collided) {
            this.vAcceleration = -this.vAccelerationLimit;
        }
    }

    @Override
    public void move() {
        this.moveVertically();
    }

    @Override
    public void paint() {
        this.update();
        serviceLocator.getRenderer().drawImage(this.sprite.getImage(), this.getXPos(), this.getYPos());
    }

    @Override
    public void update() {
        this.move();
    }


    /**
     * Move the Doodle along the X axis.
     *
     * @param direction true to go left, false to go right
     */
    private void moveHorizontally(directions direction) {
        if(direction == directions.left) {
            this.addXPos((int) -this.hSpeedUnit);
        } else if(direction == directions.right) {
            this.addXPos((int) this.hSpeedUnit);
        }
    }

    /**
     * Move the Doodle along the Y axis.
     */
    private void moveVertically() {
        // Apply gravity to the doodle
        this.applyGravity();

        // Apply the vAcceleration to the doodle
        if(this.vAcceleration < 0 && this.getYPos() < .5d * Game.HEIGHT) {
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

    @Override
    public void keyPressed(int keyCode) {
        if(keyCode == KeyCode.getKeyCode(Keys.arrowLeft) || keyCode == KeyCode.getKeyCode(Keys.a)) {
            this.moveHorizontally(directions.left);
        } else if(keyCode == KeyCode.getKeyCode(Keys.arrowRight) || keyCode == KeyCode.getKeyCode(Keys.d)) {
            this.moveHorizontally(directions.right);
        }
    }

}
