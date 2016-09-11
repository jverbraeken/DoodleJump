package objects.blocks;

import system.IServiceLocator;

public final class BlockFactory implements IBlockFactory {
    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;
    private float screenWidth;
    private float screenHeight;
    private int blockNumber;

    public static void register(float screenWidth, float screenHeight, IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new BlockFactory(screenWidth, screenHeight, serviceLocator));
    }

    /**
     * Create a BlockFactory.
     * @param screenWidth = the Width of the screen
     * @param screenHeight = the Height of the screen
     */
    private BlockFactory(float screenWidth, float screenHeight, IServiceLocator serviceLocator) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.serviceLocator = serviceLocator;
        this.blockNumber = 0;
    }

    /**
     * Create a new Block object and return that.
     * @return The newly created Block
     */
    public Block createBlock(){
        Block b = new Block(screenWidth, screenHeight, serviceLocator, blockNumber);
        blockNumber++;
        return b;
    }
}
