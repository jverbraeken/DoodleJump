package rendering;

import scenes.IScene;
import system.IServiceLocator;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public final class Renderer implements IRenderer {
    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new Renderer());
    }

    private Renderer() {
    }

    @Override
    public void setScene(IScene currentScene) {

    }

    @Override
    public void start() {
        for(int x = 0; x<100; x++){
                System.out.println("YO");
        }
    }
}
