package objects.doodles;

import objects.IGameObject;

public interface IDoodle extends IGameObject {

    boolean collide(IGameObject collidee);

}
