package objects.doodles;

import audio.IAudioManager;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class DoodleFactory implements IDoodleFactory {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new DoodleFactory());
    }

    private DoodleFactory() {

    }

    public Doodle create() {
        return new Doodle();
    }

}
