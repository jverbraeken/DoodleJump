package progression;

import java.util.concurrent.Callable;

/**
 * Defines a factory creating new missions.
 */
public interface IMissionFactory {

    /**
     * Creates a new mission.
     *
     * @param type                The type of the mission
     * @param progressionObserver The class responsible for observing the correct progression attribute
     * @param times               The amount of times the doodle has to jump on a spring
     * @param action              The callable that has to be called when the mission is finished successfully
     * @return The mission created
     */
    Mission createMission(final MissionType type, final ProgressionObservers progressionObserver, final int times, final Callable<Void> action);
}
