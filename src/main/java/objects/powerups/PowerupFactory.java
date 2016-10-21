package objects.powerups;

import logging.ILogger;
import objects.IGameObject;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard implementation of the PowerupFactory. Used to generate powerups.
 */
public final class PowerupFactory implements IPowerupFactory {

    /**
     * The boosts per level of {@link ASpring spring} powerups.
     */
    private static final int[] BOOST_SPRING = new int[]{-30, -40, -50};
    /**
     * The boosts per level of {@link ATrampoline trampoline} powerups.
     */
    private static final int[] BOOST_TRAMPOLINE = new int[]{-40, -55, -70};
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The logger for the PowerupFactory class.
     */
    private final ILogger logger;
    /**
     * The spring creation observers of PowerupFactory.
     */
    private final List<ISpringCreatedObserver> springObservers = new ArrayList<>();
    /**
     * The trampoline creation observers of PowerupFactory.
     */
    private final List<ITrampolineCreatedObserver> trampolineObservers = new ArrayList<>();

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
        final Powerups type = Powerups.spring;
        final int level = serviceLocator.getProgressionManager().getPowerupLevel(type);
        final ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ASpring spring;
        switch (level) {
            case 1:
                logger.info("A new Spring has been created");
                spring = new Spring(serviceLocator, x, y, spriteFactory.getPowerupSprite(type, 1), spriteFactory.getSpringUsedSprite(1), BOOST_SPRING[level - 1]);
                break;
            case 2:
                logger.info("A new Double Spring has been created");
                spring = new Spring(serviceLocator, x, y, spriteFactory.getPowerupSprite(type, 2), spriteFactory.getSpringUsedSprite(2), BOOST_SPRING[level - 1]);
                break;
            case 3:
                logger.info("A new Titanium Spring has been created");
                spring = new Spring(serviceLocator, x, y, spriteFactory.getPowerupSprite(type, 3), spriteFactory.getSpringUsedSprite(3), BOOST_SPRING[level - 1]);
                break;
            default:
                logger.warning("The level of the " + type.name() + " is " + (level < 1 ? "lower" : "higher") + " than the PowerupFactory can handle: " + level);
                return null;
        }
        for (ISpringCreatedObserver observer : springObservers) {
            observer.alertSpringCreated(spring);
        }
        return spring;
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
        final Powerups type = Powerups.trampoline;
        final int level = serviceLocator.getProgressionManager().getPowerupLevel(type);
        final ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        ATrampoline trampoline;
        switch (level) {
            case 1:
                logger.info("A new Spring has been created");
                trampoline = new Trampoline(serviceLocator, x, y, spriteFactory.getPowerupSprite(type, 1), spriteFactory.getTrampolineUsedSprite(1), BOOST_TRAMPOLINE[level - 1]);
                break;
            case 2:
                logger.info("A new Double Spring has been created");
                trampoline = new Trampoline(serviceLocator, x, y, spriteFactory.getPowerupSprite(type, 2), spriteFactory.getTrampolineUsedSprite(2), BOOST_TRAMPOLINE[level - 1]);
                break;
            case 3:
                logger.info("A new Titanium Spring has been created");
                trampoline = new Trampoline(serviceLocator, x, y, spriteFactory.getPowerupSprite(type, 3), spriteFactory.getTrampolineUsedSprite(3), BOOST_TRAMPOLINE[level - 1]);
                break;
            default:
                logger.warning("The level of the " + type.name() + " is " + (level < 1 ? "lower" : "higher") + " than the PowerupFactory can handle: " + level);
                return null;
        }
        for (ITrampolineCreatedObserver observer : trampolineObservers) {
            observer.trampolineCreated(trampoline);
        }
        return trampoline;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(ISpringCreatedObserver springCreatedObserver) {
        if (springCreatedObserver == null) {
            final String error = "The springCreatedObserver cannot be null";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.springObservers.add(springCreatedObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(ITrampolineCreatedObserver trampolineCreatedObserver) {
        if (trampolineCreatedObserver == null) {
            final String error = "Cannot add a null trampolineCreatedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.trampolineObservers.add(trampolineCreatedObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(ISpringCreatedObserver springCreatedObserver) {
        if (springCreatedObserver == null) {
            final String error = "Cannot removed a null springCreatedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.trampolineObservers.remove(springCreatedObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(ITrampolineCreatedObserver trampolineCreatedObserver) {
        if (trampolineCreatedObserver == null) {
            final String error = "Cannot removed a null trampolineCreatedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.trampolineObservers.remove(trampolineCreatedObserver);
    }
}
