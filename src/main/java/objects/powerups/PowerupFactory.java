package objects.powerups;

import audio.IAudioManager;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class PowerupFactory implements IPowerupFactory {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new PowerupFactory());
    }

    private PowerupFactory() {

    }
}
