package scenes;

import system.IServiceLocator;

/**
 * This class is a factory that creates scenes.
 */
public final class SceneFactory implements ISceneFactory {
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Prevents instantiation from outside the class.
     */
    private SceneFactory() {
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        SceneFactory.serviceLocator = sL;
        SceneFactory.serviceLocator.provide(new SceneFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Menu newMenu() {
        return new Menu(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World newWorld() {
        return new World(serviceLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KillScreen newKillScreen() {
        return new KillScreen(serviceLocator);
    }

}
