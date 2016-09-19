package objects.blocks;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import system.IServiceLocator;

public final class BlockFactory implements IBlockFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        BlockFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new BlockFactory());
    }

    /**
     * Prevent instantiations of BlockFactory.
     */
    private BlockFactory() { }

    /** {@inheritDoc} */
    @Override
    public IBlock createStartBlock() {
        IBlock block = new StartBlock(serviceLocator);
        return block;
    }

    /** {@inheritDoc} */
    @Override
    public IBlock createBlock(IGameObject lastObject) {
        Block block = new Block(serviceLocator, lastObject);
        return block;
    }

}
