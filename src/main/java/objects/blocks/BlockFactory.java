package objects.blocks;

import system.IServiceLocator;

public final class BlockFactory implements IBlockFactory {

    private static transient IServiceLocator serviceLocator;
    private int blockCounter;

    /**
     * Register the BlockFactory in the ServiceLocator
     * @param serviceLocator the ServiceLocator where the Factory has to be registered.
     */
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        BlockFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new BlockFactory());
    }

    /**
     * Create and initialize the BlockFactory
     */
    private BlockFactory() {
        this.blockCounter = 0;
    }


    /** {@inheritDoc} */
    @Override
    public IBlock createStartBlock(){
        IBlock block = new StartBlock(serviceLocator);
        blockCounter++;
        return block;
    }

    /** {@inheritDoc} */
    @Override
    public IBlock createBlock(){
        Block block = new Block(serviceLocator, blockCounter);
        blockCounter++;
        return block;
    }

}
