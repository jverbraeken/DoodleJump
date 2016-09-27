package logging;

import filesystem.IFileSystem;
import system.IServiceLocator;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Standard implementation of the LoggingFactory. Used to create loggers.
 */
public class LoggerFactory implements ILoggerFactory {

    /**
     * The file to which the log data should be written.
     */
    private static final String LOG_IGNORE_FILE = "logIgnore.json";
    /**
     * The file to which the log data should be written.
     */
    private static String LOG_FILE;
    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator sL;
    /**
     * The writer that's used to write to the logging file.
     */
    private final Writer logWriter;
    /**
     * The logger for LoggerFactory.
     */
    private final ILogger LOGGER;
    /**
     * A set containing the classes that should not be logged.
     */
    private final Set<Class<?>> logIgnore;

    /**
     * Hidden constructor to prevent instantiation.
     */
    private LoggerFactory() {
        LOG_FILE = LoggerFactory.sL.getConstants().getLogFile();

        IFileSystem fileSystem = LoggerFactory.sL.getFileSystem();
        fileSystem.clearFile(LOG_FILE);

        // If the LOG_FILE is not found, the game should either crash on the exception or not at all (so also
        // not when something is logged. Therefore we provide an emtpy interface instead of null to prevent
        // a {@link NullPointerException}.
        Writer fw = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {

            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        };
        try {
            fw = new FileWriter(LOG_FILE, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logWriter = new BufferedWriter(fw);

        LOGGER = new Logger(sL, this.getClass(), logWriter);

        logIgnore = new HashSet<>();
        try {
            List<String> list = (List<String>) sL.getFileSystem().parseJsonList(LOG_IGNORE_FILE, String.class);
            for (String className : list) {
                try {
                    logIgnore.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    LOGGER.warning("LoggerFactory could not find class requested to ignore logging for: " + className);
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("The file logIgnore.json requested by LoggerFactory was not found");
            e.printStackTrace();
        }
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        LoggerFactory.sL = sL;
        sL.provide(new LoggerFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILogger createLogger(Class<?> cl) {
        if (logIgnore.contains(cl)) {
            return new ILogger() {
                @Override
                public void log(String msg) {

                }

                @Override
                public void error(String msg) {

                }

                @Override
                public void error(Exception exception) {

                }

                @Override
                public void info(String msg) {

                }

                @Override
                public void warning(String msg) {

                }
            };
        } else {
            return new Logger(sL, cl, logWriter);
        }
    }

}
