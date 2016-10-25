package progression;

import objects.powerups.Trampoline;

import java.util.ArrayList;
import java.util.List;

/**
 * Implemented by classes that want to get a notification when the doodle jumped on a {@link Trampoline}.
 */
public interface ITrampolineJumpedObserver extends IJumpablePowerupObserver {
    /**
     * Called when a {@link objects.powerups.Trampoline trampoline} is used.
     */
    void alertTrampolineJumped();
}
