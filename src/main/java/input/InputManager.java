package input;

import system.IServiceLocator;

public final class InputManager implements IInputManager {
    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;
    /**
    * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
    * @param serviceLocator The IServiceLocator to which the class should offer its functionality
    */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        InputManager.serviceLocator = serviceLocator;
        serviceLocator.provide(new InputManager());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private InputManager() {

    }
}
