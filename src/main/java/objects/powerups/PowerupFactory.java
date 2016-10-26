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
     * The boosts per level of {@link Spring spring} powerups.
     */
    private static final int[] BOOST_SPRING = new int[]{-30, -40, -50};
    /**
     * The boosts per level of {@link Trampoline trampoline} powerups.
     */
    private static final int[] BOOST_TRAMPOLINE = new int[]{-40, -55, -70};
    /**
     * The time measured in frames a {@link Jetpack} is in the air.
     */
    private static final int[] MAX_TIME_JETPACK = new int[]{175, 225};
    /**
     * The Y-offset for drawing the Jetpack when on Doodle.
     */
    private static final int[] OWNED_Y_OFFSET_JETPACK = new int[]{35, -70};
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
     * The jetpack creation observers of PowerupFactory.
     */
    private final List<IJetpackCreatedObserver> jetpackObservers = new ArrayList<>();

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
        final Powerups type = Powerups.jetpack;
        final int level = serviceLocator.getProgressionManager().getPowerupLevel(type);
        assert level > 0;
        assert level <= type.getMaxLevel();
        final ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        Jetpack jetpack = new Jetpack(serviceLocator, x, y, level, spriteFactory.getJetpackActiveSprites(level), MAX_TIME_JETPACK[level - 1], OWNED_Y_OFFSET_JETPACK[level - 1]);
        logger.info("A new Jetpack of level " + level + " was created");
        for (IJetpackCreatedObserver observer : jetpackObservers) {
            observer.alertJetpackCreated(jetpack);
        }
        return jetpack;
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
        assert level > 0;
        assert level <= type.getMaxLevel();
        final ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        Spring spring = new Spring(serviceLocator, x, y, level, spriteFactory.getSpringUsedSprite(level), BOOST_SPRING[level - 1]);
        logger.info("A new Spring of level " + level + " was created");
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
        assert level > 0;
        assert level <= type.getMaxLevel();
        final ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        Trampoline trampoline = new Trampoline(serviceLocator, x, y, level, spriteFactory.getTrampolineUsedSprite(level), BOOST_TRAMPOLINE[level - 1]);
        logger.info("A new Trampoline of level " + level + " was created");
        for (ITrampolineCreatedObserver observer : trampolineObservers) {
            observer.trampolineCreated(trampoline);
        }
        return trampoline;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final ISpringCreatedObserver springCreatedObserver) {
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
    public void addObserver(final ITrampolineCreatedObserver trampolineCreatedObserver) {
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
    public void addObserver(final IJetpackCreatedObserver jetpackCreatedObserver) {
        if (jetpackCreatedObserver == null) {
            final String error = "Cannot add a null jetpackCreatedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.jetpackObservers.add(jetpackCreatedObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final ISpringCreatedObserver springCreatedObserver) {
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
    public void removeObserver(final ITrampolineCreatedObserver trampolineCreatedObserver) {
        if (trampolineCreatedObserver == null) {
            final String error = "Cannot removed a null trampolineCreatedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.trampolineObservers.remove(trampolineCreatedObserver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final IJetpackCreatedObserver jetpackCreatedObserver) {
        if (jetpackCreatedObserver == null) {
            final String error = "Cannot removed a null jetpackCreatedObserver";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        this.jetpackObservers.remove(jetpackCreatedObserver);
    }

}
