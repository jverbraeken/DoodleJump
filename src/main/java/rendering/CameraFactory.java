package rendering;

import logging.ILogger;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * A factory for the creation of different cameras.
 */
public final class CameraFactory implements ICameraFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the SceneFactory class.
     */
    private final ILogger logger;

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        CameraFactory.serviceLocator = sL;
        CameraFactory.serviceLocator.provide(new CameraFactory());
    }

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private CameraFactory() {
        this.logger = CameraFactory.serviceLocator.getLoggerFactory().createLogger(CameraFactory.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICamera createStaticCamera() {
        this.logger.info("A new StaticCamera has been created");
        return new StaticCamera();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICamera createDoodleCamera(final IDoodle doodle) {
        this.logger.info("A new DoodleCamera has been created");
        return new DoodleCamera(serviceLocator, doodle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICamera createArcadeCamera() {
        this.logger.info("A new ArcadeCamera has been created");
        return new ArcadeCamera();
    }

}
