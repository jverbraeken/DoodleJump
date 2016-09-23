package logging;

/**
 * Interface for a Console logger.
 */
/* package */ interface IConsole {

    /**
     * Log a message.
     *
     * @param msg The message to print.
     */
    static void log(final String msg) { }

    /**
     * Print an error.
     *
     * @param msg The message to print.
     */
    static void error(final String msg) { }

    /**
     * Print information.
     *
     * @param msg The message to print.
     */
    static void info(final String msg) { }

    /**
     * Print a warning.
     *
     * @param msg The message to print.
     */
    static void warning(final String msg) { }

}
