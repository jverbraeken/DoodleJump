package rendering;

import objects.doodles.Doodle;
import objects.doodles.DoodleFactory;
import objects.doodles.IDoodleFactory;
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
        Renderer.serviceLocator = serviceLocator;
    }

    private Renderer() {
    }

    @Override
    public void setScene(IScene currentScene) {

    }

    @Override
    public void start() {
        IDoodleFactory a = serviceLocator.getDoodleFactory();
        Doodle b = a.create();
        System.out.println(b.acceleration);
        b.move();
        System.out.println(b.acceleration);

        for(int x = 0; x<1; x++){
            System.out.println("YO");
        }
    }
}
