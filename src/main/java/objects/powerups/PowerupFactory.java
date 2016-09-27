package objects.powerups;

import logging.ILogger;
import objects.IGameObject;
import system.IServiceLocator;

/**
 * Standard implementation of the PowerupFactory. Used to generate powerups.
 */
public final class PowerupFactory implements IPowerupFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;
    /**
     * The logger for the PowerupFactory class.
     */
    private final ILogger LOGGER;
    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        PowerupFactory.sL = sL;
        PowerupFactory.sL.provide(new PowerupFactory());
    }

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private PowerupFactory() {
        LOGGER = sL.getLoggerFactory().createLogger(PowerupFactory.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createSpring(final int x, final int y) {
        LOGGER.info("A new Spring has been created");
        return new Spring(sL, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createTrampoline(final int x, final int y) {
        LOGGER.info("A new Trampoline has been created");
        return new Trampoline(sL, x, y);
    }

}
