package objects.doodles;

import scenes.World;
import system.IFactory;

/**
 * This is a factory creating all doodles.
 */
public interface IDoodleFactory extends IFactory {

    /**
     * Create a new Doodle.
     *
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
