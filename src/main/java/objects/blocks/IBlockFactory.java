package objects.blocks;

import system.IFactory;

public interface IBlockFactory extends IFactory {

    /**
     * Creates and returns a newly created Block.
     * @return a newly created Block
     */
    IBlock createBlock();

    /**
     * Creates and returns a newly created StartBlock.
     * This is the first Block of the game
     * @return a newly created StartBlock
     */
    IBlock createStartBlock();

    
}
