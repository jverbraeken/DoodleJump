package objects;

import objects.blocks.platform.IPlatform;
import objects.doodles.IDoodle;

public class Collisions {
    // TODO: still needs to take the width of an object into consideration.
    public void collision(IGameObject mover, IGameObject collided) {
        if((mover.getXPos() == collided.getXPos()) && (mover.getXPos() == collided.getXPos())){
            collide(mover, collided);
        }
    }

    public void  collide(IGameObject mover, IGameObject collided) {
        if (mover instanceof IDoodle) {
            playerCollision(mover, collided);
        }
    }

    public void playerCollision(IGameObject mover, IGameObject collided) {
        if (collided instanceof IPlatform) {
            IDoodle player = (IDoodle) mover;
            IPlatform platform = (IPlatform) collided;
            player.collide(platform);
        }
        /*else if(collided instanceof Enemy){
            Doodle player = (Doodle) mover;
            if(player.getVAccelleration() <= 0.0){
                player.setVAccel(6.0f);
                // TODO: still needs code to remove the enemy from the field
            }
            else {
                player.setAlive();
            }
        }*/
    }

}