package math;

import resources.audio.IAudioManager;
import system.IServiceLocator;

import java.util.Random;

public final class Calc implements ICalc {

    /**
     * Used to gain access to all services.
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
