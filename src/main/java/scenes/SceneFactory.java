package scenes;

import system.IServiceLocator;

public final class SceneFactory implements ISceneFactory {
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new SceneFactory());
    }

    /**
     * Prevents instantiation from outside the class.
     */
    private SceneFactory() {
    }

    @Override
    public Menu newMenu() {
        return new Menu(serviceLocator);
    }

    @Override
    public World newWorld() {
        return new World(serviceLocator);
    }

}
