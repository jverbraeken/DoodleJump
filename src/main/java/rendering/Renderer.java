package rendering;

import objects.powerups.PowerupFactory;
import scenes.IScene;
import system.IServiceLocator;

/**
 * Created by joost on 6-9-16.
 */
public final class Renderer implements IRenderer {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new Renderer());
    }

    private IScene currentScene;

    private Renderer() {

    }

    /** {@inheritDoc} */
    @Override
    public void setScene(IScene currentScene) {
        assert currentScene != null;
        this.currentScene = currentScene;
    }

    @Override
    public void start() {

    }
}
