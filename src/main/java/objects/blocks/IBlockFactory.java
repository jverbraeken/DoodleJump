package objects.blocks;

import system.IFactory;

public interface IBlockFactory extends IFactory {

    IBlock createBlock(double lastPlatformHeight);
    IBlock createStartBlock();

    
}
