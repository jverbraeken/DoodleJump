package objects.blocks;

import objects.blocks.platform.IPlatform;
import system.IFactory;

public interface IBlockFactory extends IFactory {

    IBlock createBlock(IPlatform lastPlatform);
    IBlock createStartBlock();

    
}
