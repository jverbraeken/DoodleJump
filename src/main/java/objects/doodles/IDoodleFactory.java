package objects.doodles;

import system.IFactory;

/**
 * This is a factory creating all doodles.
 */
public interface IDoodleFactory extends IFactory {

    /**
     * Create a new doodle.
     * @return the new doodle.
     */
    IDoodle createDoodle();

}
