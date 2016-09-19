package logging;

public class Logger implements ILogger {

    /**
     * Hidden constructor to prevent instantiation.
     */
    private Logger() { }

    /** {@inheritDoc} */
    public static void log(final String msg) {
        Console.log(msg);
        // TODO: Write to file
    }

    /** {@inheritDoc} */
    static void error(final String msg) {
        Console.error(msg);
        // TODO: Write to file
    }

    /** {@inheritDoc} */
    static void info(final String msg) {
        Console.info(msg);
        // TODO: Write to file
    }

    /** {@inheritDoc} */
    static void warning(final String msg) {
        Console.warning(msg);
        // TODO: Write to file
    }
}
