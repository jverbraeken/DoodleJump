package objects.platform;

import objects.BlockFactory;
import system.IServiceLocator;

/**
 * Created by Nick on 7-9-2016.
 */
public class PlatformFactory implements IPlatformFactory {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new PlatformFactory());
    }

    private PlatformFactory() {

    }
}
