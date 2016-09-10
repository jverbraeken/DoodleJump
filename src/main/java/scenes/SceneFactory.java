package scenes;

import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import objects.powerups.IPowerupFactory;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class SceneFactory implements ISceneFactory {

    private static transient IServiceLocator serviceLocator;
    private final Menu menu = new Menu();
    private World world;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new SceneFactory());
        SceneFactory.serviceLocator = serviceLocator;
    }

    private SceneFactory() {
    }

    @Override
    public Menu getMenu() {
        return null;
    }

    @Override
    public World getNewWorld() {
        IDoodleFactory factory = serviceLocator.getDoodleFactory();
        IDoodle doodle = factory.create();
        return new World(serviceLocator.getBlockFactory(),doodle);
    }

}
