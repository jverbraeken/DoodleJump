package logging;

public final class Console implements IConsole {

    /**
     * Hidden constructor to prevent instantiation.
     */
    private Console() { }

    /** {@inheritDoc} */
    public static void log(final String msg) {
        System.out.println(msg);
    }

    /** {@inheritDoc} */
    static void error(final String msg) {
        System.out.print("ERROR: ");
        System.out.println(msg);
    }

    /** {@inheritDoc} */
    static void info(final String msg) {
        System.out.print("INFO: ");
        System.out.println(msg);
    }

    /** {@inheritDoc} */
    static void warning(final String msg) {
        System.out.print("WARNING: ");
        System.out.println(msg);
    }

}
