package logging;

import filesystem.IFileSystem;
import system.Game;
import system.IServiceLocator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Standard implementation of the LoggingFactory. Used to create loggers.
 */
public class LoggerFactory implements ILoggerFactory {

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator sL;
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
     * Hidden constructor to prevent instantiation.
     */
    private LoggerFactory() {
        if (Game.CLEARLOGONSTARTUP) {
            IFileSystem fileSystem = LoggerFactory.sL.getFileSystem();
            fileSystem.clearFile(Game.LOGFILENAME);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILogger createLogger(Class<?> cl) {
        return new Logger(sL, cl);
    }

}
