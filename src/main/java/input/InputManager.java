package input;

import system.IServiceLocator;

public final class InputManager implements IInputManager {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new InputManager());
    }

    private InputManager() {

    }
}
