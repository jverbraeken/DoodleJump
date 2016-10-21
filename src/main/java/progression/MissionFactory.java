package progression;

import logging.ILogger;
import system.IServiceLocator;

import java.util.concurrent.Callable;

public final class MissionFactory implements IMissionFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * The logger
     */
    private final ILogger logger;

    /**
     * Prevents instantiation from outside the class.
     */
    private MissionFactory() {
        this.logger = serviceLocator.getLoggerFactory().createLogger(this.getClass());
    }

    /**
     * Register the FileSystem into the service locator.
     *
     * @param serviceLocator the service locator.
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        MissionFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new MissionFactory());
    }

    public Mission createMission(final MissionType type, final ProgressionObservers progressionObserver, final int times, Callable<Void> action) {
        IProgressionObserver observer;
        switch (type) {
            case jumpOnSpring:
                observer = new SpringUsedObserver(serviceLocator, progressionObserver, times, action);
                break;
            default:
                final String error = "The mission type\"" + type.name() + "\" could not be identified";
                logger.error("The mission type\"" + type.name() + "\" could not be identified");
                throw new UnknownMissionTypeException(error);
        }

        final String message = type.getMessage(times);
        Mission mission = new Mission(serviceLocator, type, times, message, observer);

        observer.setMission(mission);

        return mission;
    }

    private class UnknownMissionTypeException extends RuntimeException {
        private UnknownMissionTypeException(String message) {
            super(message);
        }
    }
}
