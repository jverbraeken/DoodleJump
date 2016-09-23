package math;

import system.IServiceLocator;

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
     * Prevents instantiation from outside the class.
     */
    private Calc() {
    }

    /**
     * Register the FileSystem into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Calc.serviceLocator = sL;
        Calc.serviceLocator.provide(new Calc());
    }
}
