package audio;

import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class SoundFactory implements ISoundFactory {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new SoundFactory());
    }

    private SoundFactory() {

    }
}
