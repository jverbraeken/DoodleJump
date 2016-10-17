package math;

import system.IServiceLocator;

import java.util.Random;

/**
 * This class handles all advanced calculations.
 * Examples are random integers and doubles.
 */
public final class Calc implements ICalc {

    /**
     * Random generator.
     */
    private static final Random RANDOM = new Random();

    /**
     * Prevents instantiation from outside the class.
     */
    private Calc() { }

    /**
     * Register the FileSystem into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        sL.provide(new Calc());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRandomIntBetween(final int lower, final int upper) {
        if (upper <= lower) {
            throw new IllegalArgumentException("The upper-bound cannot equal the lower-bound");
        }

        return RANDOM.nextInt(upper - lower) + lower + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getRandomDouble(final double max) {
        if (max <= 0) {
            throw new IllegalArgumentException("The maximum value for a random double should be more than 0, but was [" + max + "] instead");
        }

        return RANDOM.nextDouble() * max;
    }

}
