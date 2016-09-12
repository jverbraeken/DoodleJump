package objects.blocks;

import system.IFactory;

public interface IBlockFactory extends IFactory {

    IBlock createBlock();
    IBlock createStartBlock();

    
}
