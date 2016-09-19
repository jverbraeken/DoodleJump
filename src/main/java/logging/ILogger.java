package logging;

public interface ILogger {

    /**
     * Log a message to the Console and FileSystem
     *
     * @param msg The message to log.
     */
    void log(final String msg);

    /**
     * Print an error to the Console and FileSystem.
     *
     * @param msg The message to log.
     */
    void error(final String msg);

    /**
     * Print information to the Console and FileSystem
     *
     * @param msg The message to log.
     */
    void info(final String msg);

    /**
     * Print a warning to the Console and FileSystem
     *
     * @param msg The message to log.
     */
    void warning(final String msg);

}
