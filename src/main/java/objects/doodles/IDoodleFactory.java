package objects.doodles;

import scenes.World;
import system.IFactory;

/**
 * This is a factory creating all Doodles.
 */
public interface IDoodleFactory extends IFactory {

    /**
     * Create a new Doodle.
     *
     * @param world The world the Doodle will live in.
     * @return The new Doodle.
     */
    IDoodle createDoodle(final World world);

    /**
     * Create a new Doodle for the StartScreen.
     *
     * @return The new Doodle.
     */
    IDoodle createStartScreenDoodle();

}
