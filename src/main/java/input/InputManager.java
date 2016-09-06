package input;

import audio.IAudioManager;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class InputManager implements IInputManager {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new InputManager());
    }

    private InputManager() {

    }
}
