package objects.blocks;

import objects.IGameObject;

import java.util.HashSet;

public interface IBlock extends IGameObject {
    /**
     * Get the content, the objects of that block.
     * @return the contents of this Block.
     */
    HashSet<IGameObject> getContent();

    /**
     * Will create and place platforms in the list of objects.
     */
    void placePlatforms();
}
