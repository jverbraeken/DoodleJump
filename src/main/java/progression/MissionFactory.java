package progression;

import logging.ILogger;
import system.IServiceLocator;

import java.util.concurrent.Callable;

/**
 * Responsible for the creation of new missions.
 */
public final class MissionFactory implements IMissionFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * The logger.
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
     * @param serviceLocator the service locator
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        MissionFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new MissionFactory());
    }

    /**
     * Creates a new mission.
     *
     * @param type                The type of the mission
     * @param progressionObserver The class responsible for observing the correct progression attribute
     * @param times               The amount of times the doodle has to jump on a spring
     * @param action              The callable that has to be called when the mission is finished successfully
     * @return A new mission
     */
    public Mission createMission(final MissionType type, final ProgressionObservers progressionObserver, final int times, final Callable<Void> action) {
        IProgressionObserver observer;
        switch (type) {
            case jumpOnSpring:
                observer = new SpringUsedObserver(serviceLocator, times, action);
                break;
            default:
                final String error = "The mission type\"" + type.name() + "\" could not be identified";
                logger.error("The mission type\"" + type.name() + "\" could not be identified");
                throw new UnknownMissionTypeException(error);
        }

        final String message = type.getMessage(times);
        Mission mission = new Mission(serviceLocator, type, message, observer);

        observer.setMission(mission);

        return mission;
    }

    /**
     * Thrown when the MissionFactory was not able to construct a mission with the given type.
     */
    private final class UnknownMissionTypeException extends RuntimeException {
        /**
         * Construct a new UnknownMissionTypeException with a certain message.
         *
         * @param message The message describing the exception
         */
        private UnknownMissionTypeException(final String message) {
            super(message);
        }
    }
}
