package objects.doodles;

public interface IDoodleFactory {

    /**
     * Creates and returns a new IDoodle. Note that multiple doodles are possible.
     * @return A new Doodle
     */
    IDoodle newDoodle();
}
