package logging;

import filesystem.IFileSystem;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.*;

/* package */ final class Logger implements ILogger {

    /**
     * Reference to the file system to write.
     */
    private static IFileSystem fileSystem;

    /**
     * Reference to the class of this logger.
     */
    private final Class cl;

    /**
     * The file to which the log data should be written
     */
    private static final String LOGFILE = "async.log";

    /**
     * The ThreadPoolExecutor responsible for executing all logging code on a seperate thread to prevent stalling of the game.
     */
    private static final ThreadPoolExecutor loggingThreadExecutor = new ThreadPoolExecutor(0, 50000, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());

    /**
     * Only create Logger in LoggerFactory.
     */
    /* package */ Logger(IServiceLocator sL, Class<?> cl) {
        Logger.fileSystem = sL.getFileSystem();
        this.cl = cl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(final String msg) {
        String str = this.generateMessage("LOG", msg);
        appendStringToTextFile(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String msg) {
        String str = this.generateMessage("ERROR", msg);
        appendStringToTextFile(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String msg) {
        String str = this.generateMessage("INFO", msg);
        appendStringToTextFile(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warning(final String msg) {
        String str = this.generateMessage("WARNING", msg);
        appendStringToTextFile(str);
    }


    private void appendStringToTextFile(final String str) {
        Runnable runnable = () -> {
            try {
                fileSystem.appendToTextFile(LOGFILE, str);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        };
        loggingThreadExecutor.execute(runnable);
    }

    /**
     * Generate the full message to log.
     *
     * @param type The type of message.
     * @param msg  The message to log.
     * @return The generated message.
     */
    private String generateMessage(final String type, final String msg) {
        Date date = new java.util.Date();
        return new Timestamp(date.getTime()) + " | " +
                "ORIGIN: '" + cl.getName() + "' | " +
                type + ": '" + msg + "'";
    }

}
