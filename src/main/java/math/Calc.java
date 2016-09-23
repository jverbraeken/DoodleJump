package math;

import system.IServiceLocator;

import java.util.Random;

/**
 * This class handles all advanced calculations.
 * Examples are random integers and doubles.
 */
public final class Calc implements ICalc {
    /**
     * Register the FileSystem into the service locator.
     * @param serviceLocator the service locator.
     */
    private static transient IServiceLocator serviceLocator;

    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Calc.serviceLocator = serviceLocator;
        serviceLocator.provide(new Calc());
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
