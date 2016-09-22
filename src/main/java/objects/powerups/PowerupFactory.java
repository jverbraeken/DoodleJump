package objects.powerups;

import logging.ILogger;
import objects.IGameObject;
import scenes.SceneFactory;
import system.IServiceLocator;

public final class PowerupFactory implements IPowerupFactory {

    /**
     * The service locator for the PowerupFactory class.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the PowerupFactory class.
     */
    private static ILogger LOGGER;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        PowerupFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new PowerupFactory());
        LOGGER = serviceLocator.getLoggerFactory().createLogger(PowerupFactory.class);
    }

    private PowerupFactory() { }

    /** {@inheritDoc} */
    @Override
    public IGameObject createSpring(final int x, final int y) {
        LOGGER.info("A new Spring has been created");
        return new Spring(serviceLocator, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public IGameObject createTrampoline(final int x, final int y) {
        LOGGER.info("A new Trampoline has been created");
        return new Trampoline(serviceLocator, x, y);
    }

}
