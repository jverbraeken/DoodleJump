package logging;

/**
 * Interface for a LoggerFactory.
 */
public interface ILoggerFactory {

    /**
     * Create a new logger for a class.
     */
    ILogger createLogger(final Class<?> cl);

}
