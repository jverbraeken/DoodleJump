package objects.doodles;

import system.IFactory;

/**
 * This is a factory creating all Doodles.
 */
public interface IDoodleFactory extends IFactory {

    /**
     * Create a new Doodle.
     *
     * @return The new Doodle.
     */
    IDoodle createDoodle();

    /**
     * Create a new Doodle for the StartScreen.
     *
     * @return The new Doodle.
     */
    IDoodle createStartScreenDoodle();

}
