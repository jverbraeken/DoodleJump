package objects.blocks.platform;

import system.IServiceLocator;

public class PlatformFactory implements IPlatformFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        PlatformFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new PlatformFactory());
    }

    private PlatformFactory() { }

    @Override
    public IPlatform createPlatform(int x, int y) {
        return new Platform(serviceLocator, x, y);
    }

    public IServiceLocator getServicelocator () {
        return serviceLocator;
    }

}
