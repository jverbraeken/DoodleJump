package logging;

import system.IServiceLocator;

public class LoggerFactory implements ILoggerFactory {

    /**
     * Reference to the service locator.
     */
    private static IServiceLocator serviceLocator;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        LoggerFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new LoggerFactory());
    }

    /**
     * Hidden constructor to prevent instantiation.
     */
    private LoggerFactory() { }

    /** {@inheritDoc} */
    @Override
    public ILogger createLogger(Class<?> cl) {
        return new Logger(serviceLocator, cl);
    }

}
