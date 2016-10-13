package progression;

import system.IServiceLocator;

import java.util.concurrent.Callable;

public final class MissionFactory implements IMissionFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

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

    /**
     * Prevents instantiation from outside the class.
     */
    private MissionFactory() { }

    public Mission createMissionJumpOnSpring(final int times, Callable<Void> action) {
        final IProgressionObserver observer = new DefaultProgressionObserver(times, action);

        final MissionType missionType = MissionType.jumpOnSpring;
        final String message = missionType.getMessage(times);
        Mission mission = new Mission(serviceLocator, missionType, times, message, observer);

        observer.setMission(mission);

        serviceLocator.getProgressionManager().addObserver(ProgressionObservers.spring, (ISpringUsedObserver) observer);

        return mission;
    }

}
