package objects.blocks;

import audio.IAudioManager;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class BlockFactory implements IBlockFactory {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new BlockFactory());
    }

    private BlockFactory() {

    }
}
