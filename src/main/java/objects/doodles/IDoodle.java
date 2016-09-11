package objects.doodles;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;

public interface IDoodle extends IGameObject {

    void collide(IPlatform platform);

}
