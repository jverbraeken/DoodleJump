package objects.blocks;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import system.IFactory;

public interface IBlockFactory extends IFactory {

    /**
     * Creates a new block for the game.
     *
     * @param lastPlatform The last platform from the previous block.
     * @return The new block.
     */
    IBlock createBlock(IPlatform lastPlatform);

    /**
     * Create a initial block for the game. Which
     * always has enough platforms to get started.
     *
     * @return The new block.
     */
    IBlock createStartBlock();

}
