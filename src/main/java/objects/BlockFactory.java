package objects;

import objects.blocks.Block;
import objects.blocks.IBlock;
import objects.platform.PlatformFactory;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class BlockFactory implements IBlockFactory {
    private static transient IServiceLocator serviceLocator;
    private double screenWidth;
    private double screenHeight;
    private int blockNumber;

    public static void register(double screenWidth, double screenHeight, IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new BlockFactory(screenWidth, screenHeight, serviceLocator));
    }

    /**
     * Create a BlockFactory.
     * @param screenWidth = the Width of the screen
     * @param screenHeight = the Height of the screen
     */
    private BlockFactory(double screenWidth, double screenHeight, IServiceLocator serviceLocator) {
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
