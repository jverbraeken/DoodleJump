package logging;

public interface IConsole {

    /**
     * Log a message.
     *
     * @param msg The message to print.
     */
    static void log(String msg) {
        System.out.println(msg);
    }

    /**
     * Print an error.
     *
     * @param msg The message to print.
     */
    static void error(String msg) {
        System.out.print("ERROR: ");
        System.out.println(msg);
    }

    /**
     * Print information.
     *
     * @param msg The message to print.
     */
    static void info(String msg) {
        System.out.print("INFO: ");
        System.out.println(msg);
    }

    /**
     * Print a warning.
     *
     * @param msg The message to print.
     */
    static void warning(String msg) {
        System.out.print("WARNING: ");
        System.out.println(msg);
    }

}
