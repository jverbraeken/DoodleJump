package objects.blocks;

import objects.IJumpable;
import system.IFactory;

/**
 * This class is the factory in which seperate blocks get created.
 * In here one can specify the type of block one wants to create.
 */
public interface IBlockFactory extends IFactory {

    /**
     * Creates a new block for the game.
     *
     * @param lastPlatform The last jumpable object from the previous block.
     * @return The new block.
     */
    IBlock createBlock(IJumpable lastPlatform);

    /**
     * Create a initial block for the game. Which
     * always has enough platforms to get started.
     *
     * @return The new block.
     */
    IBlock createStartBlock();

}
