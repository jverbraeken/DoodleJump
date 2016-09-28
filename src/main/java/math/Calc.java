package math;

import system.IServiceLocator;

import java.util.Random;

/**
 * This class handles all advanced calculations.
 * Examples are random integers and doubles.
 */
public final class Calc implements ICalc {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Register the FileSystem into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Calc.serviceLocator = sL;
        sL.provide(new Calc());
    }

    /**
     * Random generator.
     */
    private static final Random random = new Random();

    /**
     * Prevents instantiation from outside the class.
     */
    private Calc() {
    }

    /** {@inheritDoc} */
    @Override
    public int getRandomIntBetween(int lower, int upper) {
        assert upper > lower;
        return random.nextInt(upper - lower) + lower + 1;
    }

    /** {@inheritDoc} */
    @Override
    public double getRandomDouble(double max) {
        assert max > 0;
        return random.nextDouble() * max;
    }

}
