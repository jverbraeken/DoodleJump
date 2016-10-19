package logging;

import filesystem.IFileSystem;
import system.Game;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Standard implementation of the LoggingFactory. Used to create loggers.
 */
public final class LoggerFactory implements ILoggerFactory {

    /**
     * The file to which the log data should be written.
     */
    private static final String LOG_IGNORE_FILE = "logIgnore.json";

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;
    /**
     * The file to which the log data should be written.
     */
    private static String logFile;
    /**
     * The logger for LoggerFactory.
     */
    private final ILogger logger;
    /**
     * A set containing the classes that should not be logged.
     */
    private final Set<Class<?>> logIgnore;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     * @param sL The IServiceLocator to which the class should offer its functionality.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        LoggerFactory.serviceLocator = sL;
        LoggerFactory.serviceLocator.provide(new LoggerFactory());
    }

    /**
     * Hidden constructor to prevent instantiation.
     */
    private LoggerFactory() {
        IFileSystem fileSystem = LoggerFactory.serviceLocator.getFileSystem();

        LoggerFactory.logFile = LoggerFactory.serviceLocator.getConstants().getLogFile();
        if (Game.CLEAR_LOG_ON_STARTUP) {
            fileSystem.clearFile(logFile);
        }

        this.logger = new Logger(serviceLocator, this.getClass());

        this.logIgnore = new HashSet<>();
        try {
            List<String> ignoreList = (List<String>) fileSystem.parseJsonList(
                    LoggerFactory.LOG_IGNORE_FILE, String.class);
            for (String className : ignoreList) {
                try {
                    logIgnore.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    logger.warning("LoggerFactory could not find class requested to ignore logging for: " + className);
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("The file " + LoggerFactory.LOG_IGNORE_FILE + " requested by LoggerFactory was not found");
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILogger createLogger(final Class<?> cl) {
        if (this.logIgnore.contains(cl)) {
            return new ILogger() {

                @Override
                public void error(final String msg) {
                }

                @Override
                public void error(final Exception exception) {
                }

                @Override
                public void info(final String msg) {
                }

                @Override
                public void warning(final String msg) {
                }

            };
        } else {
            return new Logger(LoggerFactory.serviceLocator, cl);
        }
    }

}
