package objects.blocks;

import objects.IGameObject;
import system.IFactory;

/**
 * This class is the factory in which seperate blocks get created.
 * In here one can specify the type of block one wants to create.
 */
public interface IBlockFactory extends IFactory {

    /**
     * Creates a new block for the game.
     *
     * @param lastObject The last platform from the previous block.
     * @return The new block.
     */
    IBlock createBlock(IGameObject lastObject);

    /**
     * Create a initial block for the game. Which
     * always has enough platforms to get started.
     *
     * @return The new block.
     */
    IBlock createStartBlock();

}
