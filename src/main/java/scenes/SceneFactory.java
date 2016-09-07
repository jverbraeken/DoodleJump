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
        serviceLocator.provide(new SceneFactory());
    }

    private final Menu menu = new Menu();
    private World world;

    private SceneFactory() {
    }

    @Override
    public Menu getMenu() {
        return null;
    }

    @Override
    public World getNewWorld() {
        return new World();
    }
}
