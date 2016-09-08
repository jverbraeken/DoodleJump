package rendering;

import scenes.IScene;
import system.Game;
import system.IServiceLocator;

import javax.swing.*;
import java.awt.*;

public final class Renderer extends JFrame implements IRenderer {

    private static transient IServiceLocator serviceLocator;
    private int x = 0;
    public IScene scene;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new Renderer(serviceLocator.getSceneFactory().getNewWorld()));
        Renderer.serviceLocator = serviceLocator;
    }

    //TODO: add initial scene
    private Renderer(IScene startScene) {
        setSize(Game.width,Game.height);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        scene = startScene;
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
            //if(Game.running){
              scene.update();
            //}
            Graphics paper = getGraphics();
            paper.clearRect(0, 0, (int)getSize().getWidth(), (int)getSize().getHeight());
        }
    }

    @Override
    public void paint(Graphics g) {
        this.scene.paint(g);
    }

}
