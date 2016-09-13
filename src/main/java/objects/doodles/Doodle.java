package objects.doodles;

import input.IInputManager;
import input.KeyCode;
import input.Keys;
import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.powerups.IPowerup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

public class Doodle extends AGameObject implements IDoodle {

    private static IServiceLocator serviceLocator;

    private final Logger logger = LoggerFactory.getLogger(Doodle.class);

    //
    private double hSpeedLimit = 6d;
    private double hSpeed = 0d;
    private double hAcceleration = .5d;
    //

    // The sprite for the Doodle.
    private ISprite sprite;
    // The direction the Doodle is moving towards.
    private directions moving;
    // The direction the Doodle is facing.
    private directions facing;

    /**
     * Doodle constructor.
     * @param serviceLocator The service locator.
     */
     /* package */ Doodle(IServiceLocator serviceLocator) {
        Doodle.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getDoodleSprite(directions.right);

        IInputManager inputManager = serviceLocator.getInputManager();
        inputManager.addObserver(this);

        this.setXPos(Game.WIDTH / 2);
        this.setYPos(Game.HEIGHT / 2);
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());

        double[] hit = {getWidth() / 3d, 0d, 2 * getWidth() / 3d, (double) getHeight()};
        this.setHitBox(hit);
    }

    /** {@inheritDoc} */
    @Override
    public void animate() {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getDoodleSprite(this.facing);
    }

    /** {@inheritDoc} */
    @Override
    public boolean collide(IGameObject collidee) {
        if (collidee == null) {
            throw new IllegalArgumentException("collidee cannot be null");
        }

        // If one of these boolean turns false there is no intersection possible between 2 rectangles
        if (this.getXPos() + getHitBox()[0] < collidee.getXPos() + collidee.getWidth()
                && this.getXPos() + getHitBox()[2] > collidee.getXPos()
                && this.getYPos() + getHitBox()[1] < collidee.getYPos() + collidee.getHeight()
                && this.getYPos() + getHitBox()[3] > collidee.getYPos()) {

            if (collidee instanceof IPlatform || collidee instanceof IPowerup) {
                return this.getYPos() + this.getHeight() < collidee.getYPos() + collidee.getHeight();
            } else {
                return true;
            }
        }

        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void move() { this.moveHorizontally(); }

    /** {@inheritDoc} */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int) this.getXPos(), (int) this.getYPos());
    }

    /** {@inheritDoc} */
    @Override
    public void update() {
        this.animate();
        this.move();
        this.wrap();
    }

    /** {@inheritDoc} */
    @Override
    public void keyPress(int keyCode) {
        if (this.leftPressed(keyCode)) {
            this.moving = directions.left;
            this.facing = directions.left;
        } else if (this.rightPressed(keyCode)) {
            this.moving = directions.right;
            this.facing = directions.right;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void keyRelease(int keyCode) {
        if (this.leftPressed(keyCode) && this.moving == directions.left) {
            this.moving = null;
        } else if (this.rightPressed(keyCode) && this.moving == directions.right) {
            this.moving = null;
        }
    }

    /**
     * Wrap the Doodle.
     */
    private void wrap() {
        double middle = this.getXPos() + this.getWidth() / 2;
        if (middle < 0) {
            this.addXPos(Game.WIDTH);
        } else if (middle > Game.WIDTH) {
            this.addXPos(-Game.WIDTH);
        }
    }

    /**
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally() {
        if (moving == directions.left) {
            if (this.hSpeed > -this.hSpeedLimit) {
                this.hSpeed -= this.hAcceleration;
            }
        } else if (moving == directions.right) {
            if (this.hSpeed < this.hSpeedLimit) {
                this.hSpeed += this.hAcceleration;
            }
        } else {
            if (this.hSpeed < 0) {
                this.hSpeed += this.hAcceleration;
            } else if (this.hSpeed > 0) {
                this.hSpeed -= this.hAcceleration;
            }
        }

        this.addXPos((int) this.hSpeed);
    }

    /**
     * Check if the left key for the Doodle is pressed.
     *
     * @param keyCode The keyCode of the key.
     * @return A boolean indicating whether the key for left is pressed.
     */
    private boolean leftPressed(int keyCode) {
        return keyCode == KeyCode.getKeyCode(Keys.arrowLeft) ||
                keyCode == KeyCode.getKeyCode(Keys.a);
    }

    /**
     * Check if the right key for the Doodle is pressed.
     *
     * @param keyCode The keyCode of the key.
     * @return A boolean indicating whether the key for right is pressed.
     */
    private boolean rightPressed(int keyCode) {
        return keyCode == KeyCode.getKeyCode(Keys.arrowRight) ||
                keyCode == KeyCode.getKeyCode(Keys.d);
    }

}
