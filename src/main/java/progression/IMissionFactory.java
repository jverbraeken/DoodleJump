package progression;

import java.util.concurrent.Callable;

public interface IMissionFactory {
    Mission createMissionJumpOnSpring(final int times, Callable<Void> action);
}
