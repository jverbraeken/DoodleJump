package math;

public interface ICalc {
    int MICROSCONDS = 1000000;
    int NANOSECONDS = 1000000000;
    double NANOSECONDS_INV = 1 / NANOSECONDS;

    /**
     * Returns a random int within the range specified by {@code lower} and {@code upper}
     * @param lower The lower bound of the range
     * @param upper The upper bound of the range
     * @return A random integer between lower and upper
     */
    int getRandomIntBetween(int lower, int upper);

    /**
     * Returns a random double between 0 (inclusive) and {@code max} (inclusive).
     * @param max The maximum (inclusive) positive value the generated double may have
     * @return A random double between 0 (inclusive) and {@code max} (inclusive)
     */
    double getRandomDouble(double max);
}
