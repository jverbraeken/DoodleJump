package logging;

/**
 * Interface for a Logger.
 */
public interface ILogger {

    /**
     * Log a message to the FileSystem
     *
     * @param msg The message to log.
     */
    void log(final String msg);

    /**
     * Print an error to the FileSystem.
     *
     * @param msg The message to log.
     */
    void error(final String msg);

    /**
     * Print an error from an exception to the FileSystem.
     *
     * @param exception The exception to log.
     */
    void error(final Exception exception);

    /**
     * Print information to the FileSystem
     *
     * @param msg The message to log.
     */
    void info(final String msg);

    /**
     * Print a warning to the FileSystem
     *
     * @param msg The message to log.
     */
    void warning(final String msg);

}
