package objects.powerups;

import logging.ILogger;
import objects.IGameObject;
import system.IServiceLocator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
     * The jetpack creation observers of PowerupFactory.
     */
    private final List<ISizeDownCreatedObserver> sizeDownObservers = new ArrayList<>();
    /**
     * The jetpack creation observers of PowerupFactory.
     */
    private final List<ISizeUpCreatedObserver> sizeUpObservers = new ArrayList<>();
    /**
     * The jetpack creation observers of PowerupFactory.
     */
    private final List<ISpringShoesCreatedObserver> springShoesObservers = new ArrayList<>();
    /**
     * The jetpack creation observers of PowerupFactory.
     */
    private final List<IPropellerCreatedObserver> propellerObservers = new ArrayList<>();

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
        Jetpack jetpack = new Jetpack(serviceLocator, new Point(x, y), level);
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
        final Powerups type = Powerups.propeller;
        final int level = serviceLocator.getProgressionManager().getPowerupLevel(type);
        assert level > 0;
        assert level <= type.getMaxLevel();
        Propeller propeller = new Propeller(serviceLocator, new Point(x, y), level);
        logger.info("A new Propeller has been created");
        for (IPropellerCreatedObserver observer : propellerObservers) {
            observer.alertPropellerCreated(propeller);
        }
        return propeller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createSizeDown(final int x, final int y) {
        final Powerups type = Powerups.sizeDown;
        final int level = serviceLocator.getProgressionManager().getPowerupLevel(type);
        assert level > 0;
        assert level <= type.getMaxLevel();
        SizeDown sizeDown = new SizeDown(serviceLocator, new Point(x, y), level);
        logger.info("A new SizeDown has been created");
        for (ISizeDownCreatedObserver observer : sizeDownObservers) {
            observer.alertSizeDownCreated(sizeDown);
        }
        return sizeDown;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IGameObject createSizeUp(final int x, final int y) {
        final Powerups type = Powerups.sizeUp;
        final int level = serviceLocator.getProgressionManager().getPowerupLevel(type);
        assert level > 0;
        assert level <= type.getMaxLevel();
        SizeUp sizeUp = new SizeUp(serviceLocator, new Point(x, y), level);
        logger.info("A new SizeUp has been created");
        for (ISizeUpCreatedObserver observer : sizeUpObservers) {
            observer.alertSizeUpCreated(sizeUp);
        }
        return sizeUp;
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
        final Point point = new Point(x, y);
        Spring spring = new Spring(serviceLocator, point, level);
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
        final Powerups type = Powerups.springShoes;
        final int level = serviceLocator.getProgressionManager().getPowerupLevel(type);
        assert level > 0;
        assert level <= type.getMaxLevel();
        final Point point = new Point(x, y);
        SpringShoes springShoes = new SpringShoes(serviceLocator, point, level);
        for (ISpringShoesCreatedObserver observer : springShoesObservers) {
            observer.alertSpringShoesCreated(springShoes);
        }
        logger.info("A new pair of SpringShoes has been created");
        return springShoes;
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
        final Point point = new Point(x, y);
        Trampoline trampoline = new Trampoline(serviceLocator, point, level);
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
        this.springObservers.remove(springCreatedObserver);
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
