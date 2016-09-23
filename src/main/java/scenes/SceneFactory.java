package scenes;

import system.IServiceLocator;

/**
 * This class is a factory that creates scenes.
 */
public final class SceneFactory implements ISceneFactory {
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator sL;

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
        SceneFactory.sL = sL;
        SceneFactory.sL.provide(new SceneFactory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene newMenu() {
        return new Menu(sL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScene newWorld() {
        return new World(sL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KillScreen newKillScreen() {
        return new KillScreen(sL);
    }

}
