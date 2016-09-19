package logging;

public class Console implements IConsole {

    /**
     * Hidden constructor to prevent instantiation.
     */
    private Console() { }

    /** {@inheritDoc} */
    public static void log(String msg) {
        System.out.println(msg);
    }

    /** {@inheritDoc} */
    static void error(String msg) {
        System.out.print("ERROR: ");
        System.out.println(msg);
    }

    /** {@inheritDoc} */
    static void info(String msg) {
        System.out.print("INFO: ");
        System.out.println(msg);
    }

    /** {@inheritDoc} */
    static void warning(String msg) {
        System.out.print("WARNING: ");
        System.out.println(msg);
    }

}
