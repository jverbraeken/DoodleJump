package objects.doodles;

import objects.IGameObject;

/**
 * Created by joost on 6-9-16.
 */
public interface IDoodle extends IGameObject {

    boolean isAlive();

    void addYPos(double hSpeed);
}
