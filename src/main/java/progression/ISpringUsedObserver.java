package progression;


/**
 * Implemented by classes that want to get a notification when the doodle jumped on a {@link objects.powerups.Spring Spring}.
 */
public interface ISpringUsedObserver extends IJumpablePowerupObserver {
    /**
     * Called when a {@link objects.powerups.Spring spring} is used.
     */
    void alertSpringUsed();
}
