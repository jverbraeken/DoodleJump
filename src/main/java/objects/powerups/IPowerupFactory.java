package objects.powerups;

import objects.IGameObject;
import objects.blocks.platform.IPlatform;
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
     * Create a new Trampoline powerup.
     *
     * @param x The X location for the Trampoline.
     * @param y The Y location for the Trampoline.
     * @return A new Trampoline instance.
     */
    IGameObject createTrampoline(final int x, final int y);

    /**
     * Create a new Cannon powerup.
     *
     * @param x The X location for the Circus Cannon.
     * @param y The Y location for the Circus Cannon.
     * @return A new CircusCannon instance.
     */
    IGameObject createCircusCannon(final int x, final int y);

    /**
     * Create a new RocketLauncher powerup.
     *
     * @param x The X location for the RocketLauncher.
     * @param y The Y location for the RocketLauncher.
     * @return A new RocketLauncher instance.
     */
    IGameObject createRocketLauncher(final int x, final int y);

    /**
     * Chooses if a platform should spawn a trampoline or an upgrade of trampoline.
     *
     * @param platform The platform where the powerup is going to spawn.
     * @param X_OFFSET The X offset for the powerup.
     * @param Y_OFFSET The X location for the powerup.
     * @return A new Trampoline, CircusCannon or RocketLauncher instance.
     */
    IGameObject chooseTrampolineUpgrade(final IPlatform platform, final int X_OFFSET, final int Y_OFFSET, final int platformHeight);
}
