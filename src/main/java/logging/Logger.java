package logging;

import filesystem.IFileSystem;
import system.IServiceLocator;

import java.io.FileNotFoundException;

public final class Logger implements ILogger {

    private static IServiceLocator serviceLocator;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Logger.serviceLocator = serviceLocator;
        serviceLocator.provide(new Logger());
    }

    /**
     * Hidden constructor to prevent instantiation.
     */
    private Logger() { }

    /** {@inheritDoc} */
    @Override
    public void log(final String msg) {
        IFileSystem fileSystem = serviceLocator.getFileSystem();
        try {
            fileSystem.writeTextFile("async.log", msg);
        } catch(FileNotFoundException e) {
            System.out.println("Couldn't log: " + msg);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void error(final String msg) {
        IFileSystem fileSystem = serviceLocator.getFileSystem();

        try {
            fileSystem.writeTextFile("async.log", "ERROR: " + msg);
        } catch(FileNotFoundException e) {
            System.out.println("Couldn't log ERROR: " + msg);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void info(final String msg) {
        IFileSystem fileSystem = serviceLocator.getFileSystem();

        try {
            fileSystem.writeTextFile("async.log", "INFO: " + msg);
        } catch(FileNotFoundException e) {
            System.out.println("Couldn't log INFO: " + msg);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void warning(final String msg) {
        IFileSystem fileSystem = serviceLocator.getFileSystem();

        try {
            fileSystem.writeTextFile("async.log", "WARNING: " + msg);
        } catch(FileNotFoundException e) {
            System.out.println("Couldn't log WARNING: " + msg);
        }
    }

}
