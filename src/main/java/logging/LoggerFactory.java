package logging;

import system.IServiceLocator;

public class LoggerFactory implements ILoggerFactory {

    /**
     * Reference to the service locator.
     */
    private static IServiceLocator sL;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        LoggerFactory.sL = sL;
        sL.provide(new LoggerFactory());
    }

    /**
     * Hidden constructor to prevent instantiation.
     */
    private LoggerFactory() { }

    /** {@inheritDoc} */
    @Override
    public ILogger createLogger(Class<?> cl) {
        return new Logger(sL, cl);
    }

}
