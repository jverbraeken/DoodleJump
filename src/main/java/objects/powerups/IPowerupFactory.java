package objects.powerups;

import objects.IGameObject;
import progression.ISpringUsedObserver;
import progression.ITrampolineJumpedObserver;
import progression.SpringUsedObserver;
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

    void addObserver(final ISpringCreatedObserver springCreatedObserver);

    void addObserver(final ITrampolineCreatedObserver trampolineCreatedObserver);

    void removeObserver(final ISpringCreatedObserver springCreatedObserver);

    /**
     * Create a new RocketLauncher powerup.
     *
     * @param x The X location for the RocketLauncher.
     * @param y The Y location for the RocketLauncher.
     * @return A new RocketLauncher instance.
     */
    IGameObject createSpaceRocket(final int x, final int y);

    void removeObserver(final ITrampolineCreatedObserver trampolineCreatedObserver);

}
