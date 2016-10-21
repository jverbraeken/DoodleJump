package objects.powerups;

import logging.ILogger;
import objects.IGameObject;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

/**
 * Standard implementation of the PowerupFactory. Used to generate powerups.
 */
public final class PowerupFactory implements IPowerupFactory {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the PowerupFactory class.
     */
    private final ILogger logger;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private PowerupFactory() {
        logger = serviceLocator.getLoggerFactory().createLogger(PowerupFactory.class);
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        PowerupFactory.serviceLocator = sL;
        PowerupFactory.serviceLocator.provide(new PowerupFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createJetpack(final int x, final int y) {
        logger.info("A new Jetpack has been created");
        return new Jetpack(serviceLocator, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createPropeller(final int x, final int y) {
        logger.info("A new Propeller has been created");
        return new Propeller(serviceLocator, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createSizeDown(final int x, final int y) {
        logger.info("A new SizeDown has been created");
        return new SizeDown(serviceLocator, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createSizeUp(final int x, final int y) {
        logger.info("A new SizeUp has been created");
        return new SizeUp(serviceLocator, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createSpring(final int x, final int y) {
        logger.info("A new Spring has been created");
        return new Spring(serviceLocator, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createSpringShoes(final int x, final int y) {
        logger.info("A new pair of Spring Shoes has been created");
        return new SpringShoes(serviceLocator, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createTrampoline(final int x, final int y) {
        final int level = serviceLocator.getProgressionManager().getPowerupLevel(Powerups.trampoline);
        final ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        switch (level) {
            case 1:
                logger.info("A new Trampoline has been created");
                return new Trampoline(serviceLocator, x, y, spriteFactory.getPowerupSprite(Powerups.trampoline, 1), spriteFactory.getTrampolineUsedSprite(1));
            case 2:
                logger.info("A new Circus Cannon has been created");
                return new Trampoline(serviceLocator, x, y, spriteFactory.getPowerupSprite(Powerups.trampoline, 2), spriteFactory.getTrampolineUsedSprite(2));
            case 3:
                logger.info("A new Rocket Launcher has been created");
                return new Trampoline(serviceLocator, x, y, spriteFactory.getPowerupSprite(Powerups.trampoline, 3), spriteFactory.getTrampolineUsedSprite(3));
            default:
                logger.warning("The level of the trampoline is " + (level < 1 ? "lower" : "higher") + " than the PowerupFactory can handle: " + level);
                return null;
        }
    }
}
