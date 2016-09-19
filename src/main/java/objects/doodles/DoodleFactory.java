package objects.doodles;

import system.IServiceLocator;

public final class DoodleFactory implements IDoodleFactory {

    private static transient IServiceLocator serviceLocator;

    private DoodleFactory() {
    }

    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        DoodleFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new DoodleFactory());
    }

    @Override
    public IDoodle createDoodle() {
        return new Doodle(serviceLocator);
    }

}
