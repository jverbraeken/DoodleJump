package objects.powerups;

import objects.IGameObject;
import system.IFactory;

/**
 * This is a factory creating powerups.
 */
public interface IPowerupFactory extends IFactory {

    /**
     * Create a new Jetpack powerup.
     *
     * @param x The X location for the Jetpack.
     * @param y The Y location for the Jetpack.
     * @return A new Jetpack instance.
     */
    IGameObject createJetpack(final int x, final int y);

    /**
     * Create a new Jetpack Propeller.
     *
     * @param x The X location for the Propeller.
     * @param y The Y location for the Propeller.
     * @return A new Propeller instance.
     */
    IGameObject createPropeller(final int x, final int y);

    /**
     * Create a new SizeDown powerup.
     *
     * @param x The X location for the SizeDown.
     * @param y The Y location for the SizeDown.
     * @return A new SizeDown instance.
     */
    IGameObject createSizeDown(final int x, final int y);

    /**
     * Create a new SizeUp powerup.
     *
     * @param x The X location for the SizeUp.
     * @param y The Y location for the SizeUp.
     * @return A new SizeUp instance.
     */
    IGameObject createSizeUp(final int x, final int y);

    /**
     * Create a new Spring powerup.
     *
     * @param x The X location for the Spring.
     * @param y The Y location for the Spring.
     * @return A new Spring instance.
     */
    IGameObject createSpring(final int x, final int y);

    /**
     * Create a new SpringShoes powerup.
     *
     * @param x The X location for the SpringShoes.
     * @param y The Y location for the SpringShoes.
     * @return A new SpringShoes instance.
     */
    IGameObject createSpringShoes(final int x, final int y);

    /**
     * Create a new Trampoline powerup, the exact implementation dependent on the level of the Trampoline powerup.
     *
     * @param x The X location for the Trampoline.
     * @param y The Y location for the Trampoline.
     * @return A new Trampoline instance.
     */
    IGameObject createTrampoline(final int x, final int y);

    /**
     * Must be called by objects that want a notification when a new {@link Jetpack} is created.
     * <br>
     * <font color="red"><b>The method {@link #removeObserver(IJetpackCreatedObserver)} MUST be called when
     * the {@code jetpackCreatedObserver} should be deleted. Otherwise {@code springCreatedObserver} will not be deleted
     * due to a strong reference and a memory leak will occur</b></font>
     *
     * @param jetpackCreatedObserver The class that must get the notification
     */
    void addObserver(final IJetpackCreatedObserver jetpackCreatedObserver);

    /**
     * Must be called by objects that want a notification when a new {@link Spring} is created.
     * <br>
     * <font color="red"><b>The method {@link #removeObserver(ISpringCreatedObserver)} MUST be called when
     * the {@code springCreatedObserver} should be deleted. Otherwise {@code springCreatedObserver} will not be deleted
     * due to a strong reference and a memory leak will occur</b></font>
     *
     * @param springCreatedObserver The class that must get the notification
     */
    void addObserver(final ISpringCreatedObserver springCreatedObserver);

    /**
     * Must be called by objects that want a notification when a new {@link Trampoline} is created.
     * <br>
     * <font color="red"><b>The method {@link #removeObserver(ITrampolineCreatedObserver)} MUST be called when
     * the {@code trampolineCreatedObserver} should be deleted. Otherwise {@code trampolineCreatedObserver} will not be
     * deleted due to a strong reference and a memory leak will occur</b></font>
     *
     * @param trampolineCreatedObserver The class that must get the notification
     */
    void addObserver(final ITrampolineCreatedObserver trampolineCreatedObserver);

    /**
     * Must be called by objects that were register by {@link #addObserver(IJetpackCreatedObserver)} and do
     * not want to receive this notfications anymore.
     * <br>
     * Must be called when the observing object is deleted as well!
     *
     * @param jetpackCreatedObserver The class that must not get the notifications anymore
     */
    void removeObserver(final IJetpackCreatedObserver jetpackCreatedObserver);

    /**
     * Must be called by objects that were register by {@link #addObserver(ISpringCreatedObserver)} and do
     * not want to receive this notfications anymore.
     * <br>
     * Must be called when the observing object is deleted as well!
     *
     * @param springCreatedObserver The class that must not get the notifications anymore
     */
    void removeObserver(final ISpringCreatedObserver springCreatedObserver);

    /**
     * Must be called by objects that were register by {@link #addObserver(ITrampolineCreatedObserver)} and do
     * not want to receive this notfications anymore.
     * <br>
     * Must be called when the observing object is deleted as well!
     *
     * @param trampolineCreatedObserver The class that must not get the notifications anymore
     */
    void removeObserver(final ITrampolineCreatedObserver trampolineCreatedObserver);

}
