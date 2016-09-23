package math;

/**
 * This class handles all advanced calculations.
 * Examples are random integers and doubles.
 */
public interface ICalc {
    /**
     * The amount of microseconds in a second.
     */
    int MICROSCONDS = 1000000;
    /**
     * The amount of nanoseconds in a second.
     */
    int NANOSECONDS = 1000000000;
    /**
     * The amount of seconds in a nanosecond.
     */
    double NANOSECONDS_INV = 1 / NANOSECONDS;
}
