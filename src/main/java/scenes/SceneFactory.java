package scenes;

import logging.ILogger;
import system.IServiceLocator;

/**
 * This class is a factory that creates scenes.
 */
public final class SceneFactory implements ISceneFactory {

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
    private SceneFactory() {
        logger = serviceLocator.getLoggerFactory().createLogger(SceneFactory.class);
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        SceneFactory.serviceLocator = sL;
        sL.provide(new SceneFactory());
    }

    /** {@inheritDoc} */
    @Override
    public IScene createMainMenu() {
        logger.info("A new Menu has been created");
        return new Menu(serviceLocator);
    }

    /** {@inheritDoc} */
    @Override
    public IScene createKillScreen() {
        logger.info("A new KillScreen has been created");
        return new KillScreen(serviceLocator);
    }

    /** {@inheritDoc} */
    @Override
    public IScene createPauseScreen() {
        logger.info("A new PauseScreen has been created");
        return new PauseScreen(serviceLocator);
    }

    /** {@inheritDoc} */
    @Override
    public World newWorld() {
        logger.info("A new World has been created");
        return new World(serviceLocator);
    }

    /** {@inheritDoc} */
    @Override
    public ChooseMode newChooseMode() {
        logger.info("A new ChooseMode screen has been created");
        return new ChooseMode(serviceLocator);
    }

}
