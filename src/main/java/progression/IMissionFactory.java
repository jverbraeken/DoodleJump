package progression;

import java.util.concurrent.Callable;

public interface IMissionFactory {
    /**
     * Creates a new mission, progressing when the doodle jumps on a spring.
     * @param times The amount of times the doodle has to jump on a spring
     * @param action The callable that has to be called when the mission is finished successfully
     * @return
     */
    Mission createMissionJumpOnSpring(final int times, Callable<Void> action);
}
