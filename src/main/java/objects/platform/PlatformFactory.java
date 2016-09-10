package objects.platform;

import objects.BlockFactory;
import system.IServiceLocator;

/**
 * Created by Nick on 7-9-2016.
 */
public class PlatformFactory implements IPlatformFactory {
    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        PlatformFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new PlatformFactory());
    }

    private PlatformFactory() {

    }

    public IPlatform createPlatform(int x, int y){
        return new Platform(x, y);
    }
}
