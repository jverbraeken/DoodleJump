package logging;

import filesystem.IFileSystem;
import system.IServiceLocator;

import java.io.Writer;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* package */ final class Logger implements ILogger {

    /**
     * The ThreadPoolExecutor responsible for executing all logging code on a seperate thread to prevent stalling of the game.
     */
    private static final ThreadPoolExecutor loggingThreadExecutor = new ThreadPoolExecutor(0, 50000, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    /**
     * Reference to the file system to write.
     */
    private static IFileSystem fileSystem;
    /**
     * Reference to the class of this logger.
     */
    private final Class cl;
    /**
     * The writer to which the log data should be written
     */
    private final Writer writer;

    /**
     * Only create Logger in LoggerFactory.
     */
    /* package */ Logger(IServiceLocator sL, Class<?> cl, Writer writer) {
        Logger.fileSystem = sL.getFileSystem();
        this.cl = cl;
        this.writer = writer;
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
            fileSystem.appendToTextFile(writer, str);
        };
        loggingThreadExecutor.execute(runnable);long submitted = loggingThreadExecutor.getTaskCount();
        long completed = loggingThreadExecutor.getCompletedTaskCount();
        long notCompleted = submitted - completed;
        System.out.println(notCompleted);
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
