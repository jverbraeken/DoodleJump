package objects.powerups;

/**
 * Implemented by all classes that want to receive a notification when a new {@link SpringShoes} was created.
 */
public interface ISpringShoesCreatedObserver {
    /**
     * Called when a new {@link SpringShoes} is created.
     *
     * @param springShoes The new {@link SpringShoes} that was created
     */
    void alertSpringShoesCreated(final SpringShoes springShoes);
}
