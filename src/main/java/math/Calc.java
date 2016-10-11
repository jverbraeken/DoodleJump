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
     * The singleton Calc.
     * Created using double locking.
     */
    private volatile static ICalc calc;

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
        Calc.serviceLocator = sL;
        sL.provide(getCalc());
    }

    /**
     * Return the singleton calc.
     * Done using double locking.
     * @return the singleton calc
     */
    public static ICalc getCalc() {
        if (calc == null) {
            synchronized (Calc.class) {
                if (calc == null) {
                    calc = new Calc();
                }
            }
        }
        return calc;
    }

    /** {@inheritDoc} */
    @Override
    public int getRandomIntBetween(final int lower, final int upper) {
        assert upper > lower;
        return RANDOM.nextInt(upper - lower) + lower + 1;
    }

    /** {@inheritDoc} */
    @Override
    public double getRandomDouble(final double max) {
        assert max > 0;
        return RANDOM.nextDouble() * max;
    }

}
