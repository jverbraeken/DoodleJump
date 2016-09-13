package objects.blocks;

import objects.blocks.platform.IPlatform;
import system.IServiceLocator;

public final class BlockFactory implements IBlockFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        BlockFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new BlockFactory());
    }

    private BlockFactory() {

    }

    @Override
    public IBlock createStartBlock(){
        IBlock block = new StartBlock(serviceLocator);
        return block;
    }

    @Override
    public IBlock createBlock(IPlatform lastPlatform){
        Block block = new Block(serviceLocator, lastPlatform);
        return block;
    }

}
