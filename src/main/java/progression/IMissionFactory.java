package progression;

import java.util.concurrent.Callable;

public interface IMissionFactory {

    /**
     * Creates a new mission.
     * @param type The type of the mission
     * @param times The amount of times the doodle has to jump on a spring
     * @param action The callable that has to be called when the mission is finished successfully
     * @return The mission created
     */
    Mission createMission(final MissionType type, final int times, final Callable<Void> action);
}
