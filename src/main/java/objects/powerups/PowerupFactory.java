package objects.powerups;

import logging.ILogger;
import math.ICalc;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
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
     * Threshold to spawn a specific upgradable.
     */
    private static final int UPGRADE_THRESHOLD = 9900;

    /**
     * Threshold to spawn the first upgradable.
     */
    private static final int UPGRADE_FIRST_THRESHOLD = 3300;

    /**
     * Threshold to spawn the second upgradable.
     */
    private static final int UPGRADE_LAST_THRESHOLD = 6600;




    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
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
        logger.info("A new Trampoline has been created");
        return new Trampoline(serviceLocator, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createCircusCannon(final int x, final int y) {
        logger.info("A new Circus Cannon has been created");
        return new CircusCannon(serviceLocator, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createRocketLauncher(final int x, final int y) {
        logger.info("A new Circus Cannon has been created");
        return new RocketLauncher(serviceLocator, x, y);
    }


    // TODO: Should be changed to let the coin system decide which powerup should spawn.
    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject chooseTrampolineUpgrade(final IPlatform platform, final int X_OFFSET, final int Y_OFFSET, final int platformHeight) {
        ICalc calc = serviceLocator.getCalc();
        double randomDouble = calc.getRandomDouble(UPGRADE_THRESHOLD);
        final int randomNr = (int) (randomDouble);
        if ( randomNr > UPGRADE_FIRST_THRESHOLD && randomDouble <= UPGRADE_LAST_THRESHOLD) {
            return createCircusCannon(
                    (int) platform.getXPos() + X_OFFSET,
                    (int) platform.getYPos());
        } else if ( randomNr > UPGRADE_LAST_THRESHOLD) {
            return createRocketLauncher(
                    (int) platform.getXPos() + X_OFFSET,
                    (int) platform.getYPos());
        } else {
            return createTrampoline(
                    (int) platform.getXPos() + X_OFFSET,
                    (int) platform.getYPos() - platformHeight + Y_OFFSET);
        }
    }
}
