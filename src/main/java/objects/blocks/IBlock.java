package objects.blocks;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public interface IBlock extends IGameObject {

    /**
     * Get the content of the block.
     *
     * @return The content of the block.
     */
    Set<IGameObject> getContent();

    /**
     * Place platforms in the block.
     *
     * @param lastObject The last platform from the previous block.
     */
    void placePlatforms(IGameObject lastObject);

    /**
     * Checks for all the Platforms if they are under over the height
     * of the screen, if that's the case, delete that Platforms.
     */
    void cleanUpPlatforms();

}
