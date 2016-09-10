package scenes;

import objects.powerups.IPowerupFactory;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
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

    private final Menu menu = new Menu();
    private World world;

    /**
     * Prevents instantiation from outside the class.
     */
    private SceneFactory() {
    }

    @Override
    public Menu getMenu() {
        return null;
    }

    @Override
    public World getNewWorld() {
        return new World(serviceLocator.getBlockFactory(),serviceLocator.getDoodleFactory());
    }
}
