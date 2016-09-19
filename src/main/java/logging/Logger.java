package logging;

import filesystem.IFileSystem;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Date;

/* Package */ final class Logger implements ILogger {

    private static IServiceLocator serviceLocator;
    private final Class cl;

    /**
     * Only create Logger in LoggerFactory.
     */
    /* package */ Logger(IServiceLocator serviceLocator, Class cl) {
        Logger.serviceLocator = serviceLocator;
        this.cl = cl;
    }

    /** {@inheritDoc} */
    @Override
    public void log(final String msg) {
        IFileSystem fileSystem = serviceLocator.getFileSystem();

        try {
            String str = this.generateMessage("LOG", msg);
            fileSystem.appendToTextFile("async.log", str);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void error(final String msg) {
        IFileSystem fileSystem = serviceLocator.getFileSystem();

        try {
            String str = this.generateMessage("ERROR", msg);
            fileSystem.appendToTextFile("async.log", str);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void info(final String msg) {
        IFileSystem fileSystem = serviceLocator.getFileSystem();

        try {
            String str = this.generateMessage("INFO", msg);
            fileSystem.appendToTextFile("async.log", str);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void warning(final String msg) {
        IFileSystem fileSystem = serviceLocator.getFileSystem();

        try {
            String str = this.generateMessage("WARNING", msg);
            fileSystem.appendToTextFile("async.log", str);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate the full message to log.
     *
     * @param type The type of message.
     * @param msg The message to log.
     * @return The generated message.
     */
    private String generateMessage(final String type, final String msg) {
        Date date = new java.util.Date();
        return new Timestamp(date.getTime()) + " | " +
                "ORIGIN: '" + cl.getName() + "' | " +
                type + ": '" + msg + "'";
    }

}
