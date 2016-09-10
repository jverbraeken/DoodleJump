package objects.powerups;

import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class PowerupFactory implements IPowerupFactory {
    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new PowerupFactory());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private PowerupFactory() {

    }
}
