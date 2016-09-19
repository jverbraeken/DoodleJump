package logging;

/* package */ interface ILogger {

    /**
     * Log a message to the Console and FileSystem
     *
     * @param msg The message to log.
     */
    static void log(final String msg) { }

    /**
     * Print an error to the Console and FileSystem.
     *
     * @param msg The message to log.
     */
    static void error(final String msg) { }

    /**
     * Print information to the Console and FileSystem
     *
     * @param msg The message to log.
     */
    static void info(final String msg) { }

    /**
     * Print a warning to the Console and FileSystem
     *
     * @param msg The message to log.
     */
    static void warning(final String msg) { }

}
