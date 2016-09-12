package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;

public interface IDoodle extends IGameObject, IKeyInputObserver {

    boolean collide(IGameObject collidee);

}
