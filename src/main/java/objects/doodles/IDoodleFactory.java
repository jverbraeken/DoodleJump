package objects.doodles;

import scenes.World;
import system.IFactory;

/**
 * This is a factory creating all Doodles.
 */
public interface IDoodleFactory extends IFactory {

    /**
     * Create a new Doodle for a single player world.
     *
     * @param world The world the Doodle will live in.
     * @return The new Doodle.
     */
    IDoodle createSinglePlayerDoodle(final World world);

    /**
     * Create a new Doodle for a multi player world.
     *
     * @param world The world the Doodle will live in.
     * @return The new Doodle.
     */
    IDoodle createMultiPlayerDoodle(final World world, final int i);

    /**
     * Create a new Doodle for the StartScreen.
     *
     * @return The new Doodle.
     */
    IDoodle createStartScreenDoodle();

}
