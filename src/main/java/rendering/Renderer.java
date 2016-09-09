package rendering;

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
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new Renderer());
    }

    private int x = 0;
    private static IScene scene;

    //TODO: add initial scene
    private Renderer() {
        setSize(Game.height,Game.width);
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
    public void paint(Graphics g){

        g.drawRect(x,100,100,100);
        //scene.paint(g);
    }
}
