package scenes;

import system.IServiceLocator;

public final class SceneFactory implements ISceneFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        SceneFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new SceneFactory());
    }

    private SceneFactory() { }

    @Override
    public Menu newMenu() {
        return new Menu(serviceLocator);
    }

    @Override
    public World newWorld() {
        return new World(serviceLocator);
    }

    @Override
    public KillScreen newKillScreen() {
        return new KillScreen(serviceLocator);
    }

}
