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
    private final ILogger LOGGER;

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param serviceLocator The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        SceneFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new SceneFactory());
    }

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private SceneFactory() {
        LOGGER = serviceLocator.getLoggerFactory().createLogger(SceneFactory.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene createMainMenu() {
        LOGGER.info("A new Menu has been created");
        return new Menu(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene createKillScreen() {
        LOGGER.info("A new KillScreen has been created");
        return new KillScreen(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene createPauseScreen() {
        LOGGER.info("A new PauseScreen has been created");
        return new PauseScreen(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World newWorld() {
        LOGGER.info("A new World has been created");
        return new World(serviceLocator);
    }

}
