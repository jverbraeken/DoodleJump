package objects.blocks;

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
    public IBlock createBlock(double lastPlatformHeight){
        Block block = new Block(serviceLocator, lastPlatformHeight);
        return block;
    }

}
