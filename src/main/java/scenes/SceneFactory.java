package scenes;

import logging.ILogger;
import system.IServiceLocator;

public final class SceneFactory implements ISceneFactory {

    /**
     * The service locator for the pause screen.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the SceneFactory class.
     */
    private static ILogger LOGGER;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        SceneFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new SceneFactory());
        LOGGER = serviceLocator.getLoggerFactory().createLogger(SceneFactory.class);
    }

    private SceneFactory() { }

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
    public World newWorld() {
        LOGGER.info("A new World has been created");
        return new World(serviceLocator);
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

}
