package objects.blocks;

import objects.IJumpable;
import system.IFactory;

/**
 * This class is the factory in which separate blocks get created.
 * In here one can specify the type of block one wants to create.
 */
public interface IBlockFactory extends IFactory {

    /**
     * Creates a new block for the game.
     *
     * @param lastPlatform The last jumpable object from the previous block.
     * @param type the block type.
     * @return The new block.
     */
    IBlock createBlock(final IJumpable lastPlatform, final BlockTypes type, final boolean enemy) throws RuntimeException;

    /**
     * Create a initial block for the game. Which generates a line of platforms to get started.
     *
     * @return The new block.
     */
    IBlock createStartBlock();

}
