package objects.blocks;

import objects.IGameObject;
import system.IServiceLocator;

/**
 * This class is the factory in which seperate blocks get created.
 * In here one can specify the type of block one wants to create.
 */
public final class BlockFactory implements IBlockFactory {

    /**
            * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Prevent instantiations of BlockFactory.
     */
    private BlockFactory() {
    }

    /**
     * Register the block factory into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert serviceLocator != null;
        BlockFactory.serviceLocator = sL;
        BlockFactory.serviceLocator.provide(new BlockFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBlock createStartBlock() {
        IBlock block = new StartBlock(serviceLocator);
        return block;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBlock createBlock(final IGameObject lastObject) {
        Block block = new Block(serviceLocator, lastObject);
        return block;
    }

}
