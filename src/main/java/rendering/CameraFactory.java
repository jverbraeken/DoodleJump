package rendering;

import logging.ILogger;
import objects.doodles.IDoodle;
import system.IServiceLocator;

/**
 * A factory for the creation of different cameras.
 */
public class CameraFactory implements ICameraFactory {

    /**
     * The logger for the SceneFactory class.
     */
    private final ILogger logger;
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private CameraFactory() {
        logger = serviceLocator.getLoggerFactory().createLogger(CameraFactory.class);
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        CameraFactory.serviceLocator = sL;
        sL.provide(new CameraFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICamera createStaticCamera() {
        logger.info("A new StaticeCamera has been created");
        return new StaticCamera();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICamera createDoodleCamera(final IDoodle doodle) {
        logger.info("A new DoodleCamera has been created");
        return new DoodleCamera(serviceLocator, doodle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICamera createArcadeCamera() {
        logger.info("A new ArcadeCamera has been created");
        return new ArcadeCamera();
    }

}
