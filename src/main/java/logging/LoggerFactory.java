package logging;

import filesystem.IFileSystem;
import system.Game;
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
     * The logger for LoggerFactory.
     */
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;
    /**
     * A set containing the classes that should not be logged.
     */
    private final Set<Class<?>> logIgnore;

    /**
     * Hidden constructor to prevent instantiation.
     */
    private LoggerFactory() {
        LOG_FILE = LoggerFactory.serviceLocator.getConstants().getLogFile();

        if (Game.CLEAR_LOG_ON_STARTUP) {
            IFileSystem fileSystem = LoggerFactory.serviceLocator.getFileSystem();
            fileSystem.clearFile(LOG_FILE);
        }

        LOGGER = new Logger(serviceLocator, this.getClass());

        logIgnore = new HashSet<>();
        try {
            List<String> list = (List<String>) serviceLocator.getFileSystem().parseJsonList(LOG_IGNORE_FILE, String.class);
            for (String className : list) {
                try {
                    logIgnore.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    LOGGER.warning("LoggerFactory could not find class requested to ignore logging for: " + className);
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("The file " + LOG_IGNORE_FILE + " requested by LoggerFactory was not found");
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
        LoggerFactory.serviceLocator = sL;
        sL.provide(new LoggerFactory());
    }

    /** {@inheritDoc} */
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
            return new Logger(serviceLocator, cl);
        }
    }

}
