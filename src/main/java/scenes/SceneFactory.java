package scenes;

import input.Keys;
import logging.ILogger;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import rendering.ICamera;
import system.Game;
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
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        SceneFactory.serviceLocator = sL;
        sL.provide(new SceneFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene createMainMenu() {
        logger.info("A new Menu has been created");
        return new Menu(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene createKillScreen() {
        logger.info("A new KillScreen has been created");
        return new KillScreen(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene createPauseScreen() {
        logger.info("A new PauseScreen has been created");
        return new PauseScreen(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene createScoreScreen() {
        logger.info("A new ScoreScreen has been created");
        return new ScoreScreen(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World createSinglePlayerWorld() {
        logger.info("A new World has been created");
        World world = new World(serviceLocator);

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        IDoodle doodle = doodleFactory.createDoodle(world);
        world.addDoodle(doodle);

        ICamera camera = serviceLocator.getCameraFactory().createDoodleCamera(doodle);
        serviceLocator.getRenderer().setCamera(camera);
        world.addUpdatable(camera);

        Game.setPlayerMode(Game.PlayerModes.single);
        return world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World createTwoPlayerWorld() {
        logger.info("A new TwoPlayerWorld has been created");
        World world = new World(serviceLocator);
        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();

        IDoodle doodle1 = doodleFactory.createDoodle(world);
        doodle1.setKeys(Keys.arrowLeft, Keys.arrowRight);
        world.addDoodle(doodle1);
        IDoodle doodle2 = doodleFactory.createDoodle(world);
        doodle2.setKeys(Keys.a, Keys.d);
        world.addDoodle(doodle2);

        ICamera camera = serviceLocator.getCameraFactory().createArcadeCamera();
        serviceLocator.getRenderer().setCamera(camera);
        world.addUpdatable(camera);

        Game.setPlayerMode(Game.PlayerModes.multi);
        return world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChooseMode newChooseMode() {
        logger.info("A new ChooseMode screen has been created");
        return new ChooseMode(serviceLocator);
    }

}
