package objects.blocks.platform;

import objects.blocks.ElementTypes;
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
     * Create a platform of the specified type.
     * @param type the type/
     * @return a platform of the type.
     */
    IPlatform createPlatform(final ElementTypes type);

    /**
     * Create a horizontally moving platform.
     * @param x the platform's x position
     * @param y the platform's y position
     * @return a new horizontally moving platform
     */
    IPlatform createHorizontalMovingPlatform(final int x, final int y);

    /**
     * Create a vertically moving platform.
     * @param x the platform's x position
     * @param y the platform's y position
     * @return a new vertically moving platform
     */
    IPlatform createVerticalMovingPlatform(final int x, final int y);

    /**
     * Create a platform that breaks.
     * @param x the platform's x position
     * @param y the platform's y position
     * @return a new breaking platform
     */
    IPlatform createBreakPlatform(final int x, final int y);

    /**
     * Create a dark platform.
     * @param x the platform's x position
     * @param y the platform's y position
     * @return a new dark platform
     */
    IPlatform createDarknessPlatform(final int x, final int y);


}
