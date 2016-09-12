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

    public boolean collide(IGameObject collidee) {
        assert !collidee.equals(null);
        ICollisions collisions = serviceLocator.getCollisions();
        return collisions.collide(this, collidee);
    }

    @Override
    public void move() {
    }

    @Override
    public void render() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int)this.getXPos(), (int)this.getYPos());
    }

    @Override
    public void update() {
        double middle = this.getXPos() + this.getWidth() / 2;
        if(middle < 0) {
            this.addXPos(Game.WIDTH);
        } else if(middle > Game.WIDTH) {
            this.addXPos(-Game.WIDTH);
        }
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

    @Override
    public void keyPressed(int keyCode) {
        if(keyCode == KeyCode.getKeyCode(Keys.arrowLeft) || keyCode == KeyCode.getKeyCode(Keys.a)) {
            this.moveHorizontally(directions.left);
        } else if(keyCode == KeyCode.getKeyCode(Keys.arrowRight) || keyCode == KeyCode.getKeyCode(Keys.d)) {
            this.moveHorizontally(directions.right);
        }
    }
}
