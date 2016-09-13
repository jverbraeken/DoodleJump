package objects.blocks;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;

import java.util.ArrayList;
import java.util.HashSet;

public interface IBlock extends IGameObject {
    ArrayList<IGameObject> getContent();
    void placePlatforms(IPlatform last);
}
