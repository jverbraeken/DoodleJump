package logging;

public interface ILoggerFactory {

    /**
     * Create a new logger for a class.
     */
    ILogger createLogger(Class cl);

}
