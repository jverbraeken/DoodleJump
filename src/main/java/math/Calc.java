package math;

import resources.audio.IAudioManager;
import system.IServiceLocator;

public final class Calc implements ICalc {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new Calc());
    }

    private Calc() {

    }
}
