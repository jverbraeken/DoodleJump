package logging;

/**
 * Interface for a LoggerFactory.
 */
public interface ILoggerFactory {

    /**
     * Create a new Logger for a class.
     *
     * @param cl The class to create the Logger for.
     * @return A new Logger.
     */
    ILogger createLogger(final Class<?> cl);

}
