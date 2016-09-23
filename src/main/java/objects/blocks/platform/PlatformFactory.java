package objects.blocks.platform;

import system.IServiceLocator;

/**
 * This class is a factory that produces platforms.
 */
public final class PlatformFactory implements IPlatformFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Prevent instantiations of PlatformFactory.
     */
    private PlatformFactory() {
    }

    /**
     * Register the block factory into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        PlatformFactory.serviceLocator = sL;
        serviceLocator.provide(new PlatformFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform createPlatform(final int x, final int y) {
        return new Platform(serviceLocator, x, y);
    }

}
