package rendering;

import resources.sprites.ISpriteFactory;
import scenes.IScene;
import system.Game;
import system.IServiceLocator;

import javax.swing.*;
import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public final class Renderer extends JFrame implements IRenderer {

    private static transient IServiceLocator serviceLocator;

    private static IScene scene;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Renderer.serviceLocator = serviceLocator;
        serviceLocator.provide(new Renderer());
    }

    private Renderer() {
        setSize(Game.width, Game.height);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void setScene(IScene scene) {
        assert scene != null;
        Renderer.scene = scene;
    }

    @Override
    public synchronized void start() {
        while(true){
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Graphics paper = getGraphics();
            paper.clearRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        if(scene != null) {
            scene.paint(g);
        }
    }
}
