package objects.doodles;

import system.IFactory;

public interface IDoodleFactory extends IFactory {
    /**
     * Creates and returns a new IDoodle. Note that multiple doodles are possible.
     * @return A new Doodle
     */
    IDoodle newDoodle();
}
