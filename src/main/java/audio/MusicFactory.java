package audio;

import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class MusicFactory implements IMusicFactory {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new MusicFactory());
    }

    private MusicFactory() {

    }
}
