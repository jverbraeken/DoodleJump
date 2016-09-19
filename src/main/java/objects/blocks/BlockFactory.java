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
        return new StartBlock(serviceLocator);
    }

    /** {@inheritDoc} */
    @Override
    public IBlock createBlock(IPlatform lastPlatform) {
        return new Block(serviceLocator, lastPlatform);
    }

}
