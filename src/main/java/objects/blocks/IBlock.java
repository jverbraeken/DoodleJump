package objects.blocks;

import objects.IGameObject;

import java.util.ArrayList;

/**
 * This class focusses on the implementation of Blocks.
 * These blocks contain the main bulk of the game objects.
 * This bulk contains the platforms, powerups, enemies and other interactable items.
 * These blocks are meant to pass through our frame vertically.
 * The player is meant to progress from one block to the next by jumping on things.
 * These things can be anything as specified by "bulk".
 * The choice for block was made as to make seperate sub-levels in a continuous world.
 */
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
