package objects.blocks;

import objects.IGameObject;

import java.util.HashSet;

public interface IBlock extends IGameObject {
    HashSet<IGameObject> getContent();
    void placePlatforms();
}
