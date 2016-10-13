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
        IProgressionObserver observer = new ProgressionObserver(times, action);
        return new Mission(serviceLocator, MissionType.jumpOnSpring, times, observer);

    }

}
