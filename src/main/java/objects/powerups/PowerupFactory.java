package objects.powerups;

import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class PowerupFactory implements IPowerupFactory {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new PowerupFactory());
    }

    private PowerupFactory() {

    }
}
