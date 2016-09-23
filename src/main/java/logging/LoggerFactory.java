package logging;

import filesystem.IFileSystem;
import system.IServiceLocator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class LoggerFactory implements ILoggerFactory {

    /**
     * Reference to the service locator.
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
     * The file to which the log data should be written
     */
    private static final String LOGFILE = "async.log";
    private final Writer logWriter;

    /**
     * Hidden constructor to prevent instantiation.
     */
    private LoggerFactory() {
        IFileSystem fileSystem = LoggerFactory.sL.getFileSystem();
        fileSystem.clearFile(LOGFILE);

        // If the LOGFILE is not found, the game should either crash on the exception or not at all (so also
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
            fw = new FileWriter(LOGFILE, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logWriter = new BufferedWriter(fw);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILogger createLogger(Class<?> cl) {
        return new Logger(sL, cl, logWriter);
    }

}
