package objects.doodles;
import objects.GameObject;
import objects.platform.Platform;
import objects.enemies.Enemy;


public class Collisions {
   // TODO: still needs to take the width of an object into consideration.
    public void collision(GameObject mover, GameObject collided){
           if((mover.getXPos() == collided.getXPos()) && (mover.getXPos() == collided.getXPos())){
               collide(mover, collided);
           }
       }

        public void  collide(GameObject mover, GameObject collided) {
            if (mover instanceof Doodle) {
                playerCollision(mover, collided);
            }
        }

        public void playerCollision(GameObject mover, GameObject collided) {
            if (collided instanceof Platform) {
                Doodle player = (Doodle) mover;
                if(player.getVAccelleration() <= 0.0){
                    player.setVAccel(6.0f);
                }
            }
            else if(collided instanceof Enemy){
                Doodle player = (Doodle) mover;
                if(player.getVAccelleration() <= 0.0){
                    player.setVAccel(6.0f);
                    // TODO: still needs code to remove the enemy from the field
                }
                else {
                    player.setAlive();
                }
            }
        }


}
