package objects.blocks.platform;

import system.IFactory;

/**
 * This class is a factory that produces platforms.
 */
public interface IPlatformFactory extends IFactory {

    /**
     * Create a standard platform.
     * @param x the platform's x position
     * @param y the platform's y position
     * @return a new standard platform
     */
    IPlatform createPlatform(int x, int y);

}
