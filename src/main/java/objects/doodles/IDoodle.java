package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;

public interface IDoodle extends IGameObject, IKeyInputObserver {

    // Enum with directions for the Doodle.
    enum directions { left, right };

    boolean collide(IGameObject collidee);

}
