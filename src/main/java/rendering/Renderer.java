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

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Renderer.serviceLocator = serviceLocator;
        serviceLocator.provide(new Renderer());
    }

    private int x = 0;
    private static IScene scene;

    //TODO: add initial scene
    private Renderer() {
        setSize(Game.width, Game.height);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void setScene(IScene currentScene) {
        assert currentScene != null;
        scene = currentScene;
    }

    @Override
    public synchronized void start() {
        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(x>100)
                x = 0;
            x++;
            //if(Game.running){
            //scene.update(); }
            repaint();
            Graphics paper = getGraphics();
            paper.clearRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());
        }
    }

    @Override
    public void paint(Graphics g) {
        ISpriteFactory factory = serviceLocator.getSpriteFactory();
        Image doodleSprite = factory.getDoodleSprite();
        g.drawImage(doodleSprite, x, 100, null);
    }
}
