package objects.doodles;

import objects.AGameObject;
import objects.ICollisions;
import objects.IGameObject;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

public class Doodle extends AGameObject implements IDoodle {

    private static IServiceLocator serviceLocator;
    // The horizontal acceleration of the Doodle, positive if going right and negative if going left.
    private float hAcceleration = 0f;
    // The fastest the doodle can go horizontally.
    private float hAccelerationLimit = 3f;
    // How much the doodle is affected by the player.
    private float hAccelerationUnit = .15f;
    // The sprite for the doodle
    private ISprite sprite;

    /* package */ Doodle(IServiceLocator serviceLocator) {
        Doodle.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        this.sprite = spriteFactory.getDoodleSprite();

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
        this.moveHorizontally();
    }

    @Override
    public void paint() {
        serviceLocator.getRenderer().drawSprite(this.sprite, (int)this.getXPos(), (int)this.getYPos());
    }

    @Override
    public void update() {
        move();
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

        this.addXPos((int) this.hAcceleration);
    }
}
