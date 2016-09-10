package objects.doodles;
import objects.GameObject;
import objects.platform.Platform;


public class Collisions {

        public void  collide(GameObject mover, GameObject collided) {
            if (mover instanceof Doodle) {
                playerCollision(mover, collided);
            }
        }

        public void playerCollision(GameObject mover, GameObject collided) {
            if (collided instanceof Platform) {
                Doodle player = (Doodle) mover;
                player.moveVertically();
            }
        }

}
