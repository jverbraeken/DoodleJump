package filesystem;

import audio.IAudioManager;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class FileSystem implements IFileSystem {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new FileSystem());
    }

    private FileSystem() {

    }
}
