package objects.blocks;

import system.IServiceLocator;

public final class BlockFactory implements IBlockFactory {

    private static transient IServiceLocator serviceLocator;
    private int blockCounter;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        BlockFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new BlockFactory());
    }

    private BlockFactory() {
        this.blockCounter = 0;
    }

    @Override
    public IBlock createBlock(){
        Block block = new Block(serviceLocator, blockCounter);
        blockCounter++;
        return block;
    }

}
