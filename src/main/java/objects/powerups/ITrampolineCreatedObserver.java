package objects.powerups;

/**
 * Implemented by all classes that want to receive a notification when a new {@link Trampoline} was created.
 */
public interface ITrampolineCreatedObserver {
    /**
     * Called when a new {@link Trampoline} is created.
     *
     * @param trampoline The new {@link Trampoline} that was created
     */
    void trampolineCreated(final Trampoline trampoline);
}
