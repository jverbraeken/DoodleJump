package objects.doodles;

import system.IServiceLocator;


public final class DoodleFactory implements IDoodleFactory {
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
        serviceLocator.provide(new DoodleFactory());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private DoodleFactory() {

    }
}
