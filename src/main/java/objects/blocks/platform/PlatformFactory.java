package objects.blocks.platform;

import system.IServiceLocator;

public class PlatformFactory implements IPlatformFactory {

    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;

    /**
    * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
    * @param serviceLocator The IServiceLocator to which the class should offer its functionality
    */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        PlatformFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new PlatformFactory());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private PlatformFactory() { }

    /**
     * Create and initiate a Platform object
     * @param x - the X position of the platform
     * @param y - the y position of the platform
     */
    public Platform newPlatform(final int x, final int y) {
        return new Platform(x, y);
    }
}
