package objects.blocks;

import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;

import java.util.ArrayList;
import java.util.Set;

public interface IBlock extends IGameObject {

    /**
     * Get the elements of the block.
     *
     * @return The elements of the block.
     */
    Set<IGameObject> getElements();

    /**
     * @return The highest situated platform in the block
     */
    IPlatform getTopPlatform();
}
