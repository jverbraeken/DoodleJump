package objects;

import objects.platform.PlatformFactory;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class BlockFactory implements IBlockFactory {
    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new BlockFactory());
    }

    private BlockFactory() {
        serviceLocator.getPlatformFactory();
    }
}
