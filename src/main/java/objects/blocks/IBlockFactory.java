package objects.blocks;

import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import system.IFactory;

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
