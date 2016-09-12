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

import java.util.ArrayList;
import java.util.HashSet;

public class Doodle extends AGameObject implements IDoodle {

    private static IServiceLocator serviceLocator;
    // How much the doodle is affected by the player.
    private float hSpeedUnit = 10;
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
        System.out.println(sprite.getWidth());

        Double[] hit = {getWidth()/3d, 0d, 2*getWidth()/3d, (double)getHeight()};
        this.setHitBox(hit);
    }

    @Override
    public void animate() { }

    public boolean collide(IGameObject collidee) {
        assert !collidee.equals(null);
        ICollisions collisions = serviceLocator.getCollisions();

        if(this.getXPos()+getHitBox()[0] < collidee.getXPos() && collidee.getXPos() < this.getXPos()+getHitBox()[2]){
            if(this.getYPos()+getHitBox()[1] < collidee.getYPos() && collidee.getYPos() < this.getYPos()+getHitBox()[3]){
                return true;
            } else if(collidee.getYPos() < this.getYPos()+getHitBox()[1] && this.getYPos()+getHitBox()[1] < collidee.getYPos() + collidee.getWidth()){
                return true;
            }
        } else if(collidee.getXPos() < this.getXPos()+getHitBox()[0] && this.getXPos()+getHitBox()[0] < collidee.getXPos() + collidee.getWidth()){
            if(this.getYPos()+getHitBox()[1] < collidee.getYPos() && collidee.getYPos() < this.getYPos()+getHitBox()[3]){
                return true;
            } else if(collidee.getYPos() < this.getYPos()+getHitBox()[1] && this.getYPos()+getHitBox()[1] < collidee.getYPos() + collidee.getWidth()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void move() {
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
