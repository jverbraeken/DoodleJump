package objects.doodles;

import logging.ILogger;
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
     * Logger for the DoodleFactory.
     */
    private ILogger logger;

    /**
     * Prevent instantiations of DoodleFactory.
     */
    private DoodleFactory() {
        this.logger = DoodleFactory.serviceLocator.getLoggerFactory().createLogger(DoodleFactory.class);
    }

    /**
     * Register the doodle factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        DoodleFactory.serviceLocator = sL;
        DoodleFactory.serviceLocator.provide(new DoodleFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodle createDoodle(final World world) {
        logger.info("A new Doodle has been created");
        IDoodle doodle = new Doodle(serviceLocator, world);
        doodle.setVerticalSpeed(DoodleFactory.DOODLE_INITIAL_SPEED);
        return doodle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IDoodle createStartScreenDoodle() {
        logger.info("A new StartScreenDoodle has been created");
        return new StartScreenDoodle(serviceLocator);
    }

}
