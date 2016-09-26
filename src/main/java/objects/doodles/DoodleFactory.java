package objects.doodles;

import system.IServiceLocator;

/**
 * This is a factory creating all doodles.
 */
public final class DoodleFactory implements IDoodleFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;

    /**
     * Prevent instantiations of DoodleFactory.
     */
    private DoodleFactory() {
    }

    /**
     * Register the doodle factory into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        DoodleFactory.sL = sL;
        DoodleFactory.sL.provide(new DoodleFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodle createDoodle() {
        return new Doodle(sL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodle createStartScreenDoodle() {
        return new StartScreenDoodle(sL);
    }

}
