package math;

import resources.audio.IAudioManager;
import system.IServiceLocator;

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

    /**
     * Prevents instantiation from outside the class.
     */
    private Calc() {
    }
}
