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

    // How much the Doodle is affected by the player.
    private float hSpeedUnit = 5;
    // The sprite for the Doodle.
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
        this.sprite = spriteFactory.getDoodleSprite(directions.right);

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
        this.sprite = spriteFactory.getDoodleSprite(this.facing);
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
        double middle = this.getXPos() + this.getWidth() / 2;
        if(middle < 0) {
            this.addXPos(Game.WIDTH);
        } else if(middle > Game.WIDTH) {
            this.addXPos(-Game.WIDTH);
        }
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
     * Move the Doodle along the X axis.
     */
    private void moveHorizontally() {
        if(moving == directions.left) {
            this.addXPos((int) -this.hSpeedUnit);
        } else if(moving == directions.right) {
            this.addXPos((int) this.hSpeedUnit);
        }
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
