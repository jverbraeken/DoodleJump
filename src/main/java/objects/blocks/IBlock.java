package objects.blocks;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;

import java.util.HashSet;

public interface IBlock extends IGameObject {
    HashSet<IGameObject> getContent();
    void placePlatforms(IPlatform last);
}
