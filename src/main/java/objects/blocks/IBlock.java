package objects.blocks;

import objects.IGameObject;
import objects.IJumpable;
import system.IRenderable;
import system.IUpdatable;

import java.util.Set;

/**
 * This class focuses on the implementation of Blocks.
 * These blocks contain the main bulk of the game objects.
 * This bulk contains the platforms, powerups, enemies and other intractable items.
 * These blocks are meant to pass through our frame vertically.
 * The player is meant to progress from one block to the next by jumping on things.
 * These things can be anything as specified by "bulk".
 * The choice for block was made as to make separate sub-levels in a continuous world.
 */
public interface IBlock extends IRenderable, IUpdatable {

    /**
     * Get the elements of the block.
     *
     * @return The elements of the block.
     */
    Set<IGameObject> getElements();

    /**
     * Get the platform that is the highest.
     *
     * @return The highest situated {@link IJumpable jumpable} element in the block
     */
    IJumpable getTopJumpable();

}
