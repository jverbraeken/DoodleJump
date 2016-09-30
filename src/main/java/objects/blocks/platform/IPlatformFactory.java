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
    IPlatform createPlatform(final int x, final int y);

    /**
     * Create a horizontally moving platform.
     * @param x the platform's x position
     * @param y the platform's y position
     * @return a new horizontally moving platform
     */
    IPlatform createHoriMovingPlatform(final int x, final int y);

    /**
     * Create a vertically moving platform.
     * @param x the platform's x position
     * @param y the platform's y position
     * @return a new vertically moving platform
     */
    IPlatform createVertMovingPlatform(final int x, final int y);

    /**
     * Create a platform that breaks.
     * @param x the platform's x position
     * @param y the platform's y position
     * @return a new breaking platform
     */
    IPlatform createBreakPlatform(final int x, final int y);

}
