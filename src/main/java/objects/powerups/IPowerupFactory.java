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
     * Create a new Spring powerup.
     *
     * @param x The X location for the Spring.
     * @param y The Y location for the Spring.
     * @return A new Spring instance.
     */
    IGameObject createSpring(final int x, final int y);

    /**
     * Create a new JumpBoots powerup.
     *
     * @param x The X location for the JumpBoots.
     * @param y The Y location for the JumpBoots.
     * @return A new JumpBoots instance.
     */
    IGameObject createJumpBoots(final int x, final int y);

    /**
     * Create a new Trampoline powerup.
     *
     * @param x The X location for the Trampoline.
     * @param y The Y location for the Trampoline.
     * @return A new Trampoline instance.
     */
    IGameObject createTrampoline(final int x, final int y);

}
