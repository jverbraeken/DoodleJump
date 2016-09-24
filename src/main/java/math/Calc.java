package math;

import system.IServiceLocator;

import java.util.Random;

/**
 * This class handles all advanced calculations.
 * Examples are random integers and doubles.
 */
public final class Calc implements ICalc {
    private static transient IServiceLocator sL;

    /**
     * Register the FileSystem into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Calc.sL = sL;
        sL.provide(new Calc());
    }




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
        //TODO is this + 1 really necessary?
        return random.nextInt(upper - lower) + lower + 1;
    }

    /** {@inheritDoc} */
    @Override
    public double getRandomDouble(double max) {
        assert max > 0;
        return random.nextDouble() * max;
    }
}
