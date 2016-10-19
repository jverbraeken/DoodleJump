package logging;

import filesystem.IFileSystem;
import system.IServiceLocator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Standard implementation of the Logger. Used to log to a file.
 */
/* package */ final class Logger implements ILogger {

    /**
     * The ThreadPoolExecutor responsible for executing all logging code on a separate thread to prevent stalling.
     */
    private static final ThreadPoolExecutor LOGGING_THREAD_EXECUTOR = new ThreadPoolExecutor(
            0, 50000, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());

    /**
     * Reference to the file system to write.
     */
    private final IFileSystem fileSystem;
    /**
     * Reference to the class of this logger.
     */
    private final Class cl;
    /**
     * True if the amount of pending logging tasks should be logged.
     */
    private final boolean logPendingTasks;

    /**
     * Only create Logger in LoggerFactory.
     * @param sL The serviceLocator.
     * @param targetClass The class the Logger is serving.
     */
    /* package */ Logger(final IServiceLocator sL, final Class<?> targetClass) {
        this.fileSystem = sL.getFileSystem();
        this.cl = targetClass;
        this.logPendingTasks = sL.getConstants().getLogPendingTasks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final String msg) {
        String str = this.generateMessage("ERROR", msg);
        this.appendStringToTextFile(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(final Exception exception) {
        String str = this.generateMessage("ERROR", exception.getMessage());
        this.appendStringToTextFile(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(final String msg) {
        String str = this.generateMessage("INFO", msg);
        this.appendStringToTextFile(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warning(final String msg) {
        String str = this.generateMessage("WARNING", msg);
        this.appendStringToTextFile(str);
    }

    /**
     * Append a string to a text file.
     * @param str The string to append.
     */
    private void appendStringToTextFile(final String str) {
        Runnable runnable = () -> this.fileSystem.log(str);
        Logger.LOGGING_THREAD_EXECUTOR.execute(runnable);
        if (this.logPendingTasks) {
            long submitted = Logger.LOGGING_THREAD_EXECUTOR.getTaskCount();
            long completed = Logger.LOGGING_THREAD_EXECUTOR.getCompletedTaskCount();
            long notCompleted = submitted - completed;
            this.fileSystem.log("Pending logging tasks: " + notCompleted);
        }
    }

    /**
     * Generate the full message to log.
     * @param type The type of message.
     * @param msg  The message to log.
     * @return The generated message.
     */
    private String generateMessage(final String type, final String msg) {
        Date date = new Date();
        return new Timestamp(date.getTime()) + " | "
                + "ORIGIN: '" + this.cl.getName() + "' | "
                + type + ": '" + msg + "'";
    }

}
