package progression;

/**
 * Implemented by classes that want to get a notification when the doodle jumped on a {@link objects.powerups.Trampoline}.
 */
public interface ITrampolineJumpedObserver extends IJumpablePowerupObserver {
    /**
     * Called when a {@link objects.powerups.Trampoline trampoline} is used.
     */
    void alertTrampolineJumped();
}
