package objects.doodles;

import scenes.World;
import system.IServiceLocator;

/**
 * This is a factory creating all doodles.
 */
public final class DoodleFactory implements IDoodleFactory {

    /**
     * Initial vertical speed for the Doodle.
     */
    private static final int DOODLE_INITIAL_SPEED = -9;

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Prevent instantiations of DoodleFactory.
     */
    private DoodleFactory() { }

    /**
     * Register the doodle factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        DoodleFactory.serviceLocator = sL;
        sL.provide(new DoodleFactory());
    }

    /**
	 * {@inheritDoc}
     */
    @Override
    public IDoodle createDoodle(final World world) {
        IDoodle doodle = new Doodle(serviceLocator, world);
        doodle.setVerticalSpeed(DOODLE_INITIAL_SPEED);
        return doodle;
    }

    /**
	 * {@inheritDoc}
     */
    @Override
    public IDoodle createStartScreenDoodle() {
        return new StartScreenDoodle(serviceLocator);
    }

}
