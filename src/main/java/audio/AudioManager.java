package audio;

import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class AudioManager implements IAudioManager {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new AudioManager());
    }

    private AudioManager() {

    }
}
