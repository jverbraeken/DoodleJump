package math;

/**
 * Interface for the class handles all advanced calculations.
 * Examples are random integers and doubles.
 */
public interface ICalc {

    /**
     * The amount of nanoseconds in a second.
     */
    int NANOSECONDS = 1000000000;

    /**
     * Returns a random int within the range specified by {@code lower} and {@code upper}.
     *
     * @param lower The lower bound of the range.
     * @param upper The upper bound of the range.
     * @return A random integer between lower and upper.
     */
    int getRandomIntBetween(final int lower, final int upper);

    /**
     * Returns a random double between 0 (inclusive) and {@code max} (inclusive).
     *
     * @param max The maximum (inclusive) positive value the generated double may have.
     * @return A random double between 0 (inclusive) and {@code max} (inclusive).
     */
    double getRandomDouble(final double max);
}
