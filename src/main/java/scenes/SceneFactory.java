package scenes;

import objects.powerups.IPowerupFactory;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class SceneFactory implements ISceneFactory {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        SceneFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new SceneFactory());
    }

    @Override
    public Menu getStartMenu() {
        return new Menu(serviceLocator);
    }

    @Override
    public World getNewWorld() { return new World(serviceLocator); }

}
