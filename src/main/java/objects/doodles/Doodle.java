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

    // Horizontal speed limit for the Doodle.
    private double hSpeedLimit = 6d;
    // Current horizontal speed for the Doodle.
    private double hSpeed = 0d;
    // Current vertical speed for the Doodle.
    private double vSpeed = 0d;
    // Horizontal acceleration for the Doodle.
    private double hAcceleration = .5d;
    // The sprite for the Doodle.
    private ISprite[] spritePack;
    private ISprite sprite;
    // The direction the Doodle is moving towards.
    private directions moving;
    // The direction the Doodle is facing.
    private directions facing;

    /**
     * Doodle constructor.
     * <br>
     * @param serviceLocator The service locator.
     */
    /* package */ Doodle(IServiceLocator serviceLocator) {
        Doodle.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(directions.right);
        this.sprite = this.spritePack[0];

        IInputManager inputManager = serviceLocator.getInputManager();
        inputManager.addObserver(this);

        this.setXPos(Game.WIDTH / 2);
        this.setYPos(Game.HEIGHT / 2);
        this.setWidth(sprite.getWidth());
        this.setHeight(sprite.getHeight());
    }

    /** {@inheritDoc} */
    @Override
    public void animate() {
        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.spritePack = spriteFactory.getDoodleSprite(this.facing);

        if(this.vSpeed > 5) {
            this.sprite = this.spritePack[1];
        } else {
            this.sprite = this.spritePack[0];
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean collide(IGameObject collidee) {
        assert !collidee.equals(null);
        ICollisions collisions = serviceLocator.getCollisions();
        return collisions.collide(this, collidee);
    }

    /** {@inheritDoc} */
    @Override
    public void move() {
        this.moveHorizontally();
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int)this.getXPos(), (int)this.getYPos());
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
        if(this.leftPressed(keyCode)) {
            this.moving = directions.left;
            this.facing = directions.left;
        } else if(this.rightPressed(keyCode)) {
            this.moving = directions.right;
            this.facing = directions.right;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void keyRelease(int keyCode) {
        if(this.leftPressed(keyCode) && this.moving == directions.left) {
            this.moving = null;
        } else if(this.rightPressed(keyCode) && this.moving == directions.right) {
            this.moving = null;
        }
    }

    /**
     * Set the vertical speed of the Doodle.
     */
    public void setVerticalSpeed(double vSpeed) {
        this.vSpeed = vSpeed;
    }

    /**
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally() {
        if(moving == directions.left) {
            if(this.hSpeed > -this.hSpeedLimit) {
                this.hSpeed -= this.hAcceleration;
            }
        } else if(moving == directions.right) {
            if(this.hSpeed < this.hSpeedLimit) {
                this.hSpeed += this.hAcceleration;
            }
        } else {
            if(this.hSpeed < 0) {
                this.hSpeed += this.hAcceleration;
            } else if(this.hSpeed > 0) {
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

    /**
     * Wrap the Doodle around the screen.
     */
    private void wrap() {
        double middle = this.getXPos() + this.getWidth() / 2;
        if(middle < 0) {
            this.addXPos(Game.WIDTH);
        } else if(middle > Game.WIDTH) {
            this.addXPos(-Game.WIDTH);
        }
    }

}
