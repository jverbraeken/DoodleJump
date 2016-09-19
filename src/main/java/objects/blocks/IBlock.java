package objects.blocks;

import objects.IGameObject;

import java.util.ArrayList;

public interface IBlock extends IGameObject {

    /**
     * Get the content of the block.
     *
     * @return The content of the block.
     */
    ArrayList<IGameObject> getContent();

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
