package objects.blocks;

import audio.IAudioManager;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class BlockFactory implements IBlockFactory {
    private static transient IServiceLocator serviceLocator;
    private float screenWidth;
    private float screenHeight;

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
    }

    /**
     * Create a new Block object and return that.
     * @return The newly created Block
     */
    public Block createBlock(){
        return new Block(screenWidth, screenHeight, serviceLocator);
    }
}
