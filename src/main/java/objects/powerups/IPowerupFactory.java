package objects.powerups;

import objects.IGameObject;
import system.IFactory;

public interface IPowerupFactory extends IFactory {

    /**
     * Create a new Spring powerup.
     *
     * @param x The X location for the spring.
     * @param y The Y location for the spring.
     * @return A new Spring instance.
     */
    IGameObject createSpring(final int x ,final int y);

    /**
     * Create a new Trampoline powerup.
     *
     * @param x The X location for the trampoline.
     * @param y The Y location for the trampoline.
     * @return A new Trampoline instance.
     */
    IGameObject createTrampoline(final int x ,final int y);

}
