package logging;

import system.IServiceLocator;

public final class Logger implements ILogger {

    private static IServiceLocator serviceLocator;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Logger.serviceLocator = serviceLocator;
        serviceLocator.provide(new Logger());
    }

    /**
     * Hidden constructor to prevent instantiation.
     */
    private Logger() { }

    /** {@inheritDoc} */
    @Override
    public void log(final String msg) {
        Console.log(msg);
        // TODO: Write to file
    }

    /** {@inheritDoc} */
    @Override
    public void error(final String msg) {
        Console.error(msg);
        // TODO: Write to file
    }

    /** {@inheritDoc} */
    @Override
    public void info(final String msg) {
        Console.info(msg);
        // TODO: Write to file
    }

    /** {@inheritDoc} */
    @Override
    public void warning(final String msg) {
        Console.warning(msg);
        // TODO: Write to file
    }

}
