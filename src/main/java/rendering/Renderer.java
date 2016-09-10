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

    /**
    * Used to gain access to all services.
    */
    private static transient IServiceLocator serviceLocator;

    /**
    * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
    * @param serviceLocator The IServiceLocator to which the class should offer its functionality
    */
    public static void register(final IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Renderer.serviceLocator = serviceLocator;
        serviceLocator.provide(new Renderer());
    }

    private int x = 0;
    private static IScene scene;

    /**
     * Prevents instantiation from outside the class.
     */
    private Renderer() {
        setSize(Game.WIDTH, Game.HEIGHT);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void setScene(IScene currentScene) {
        assert currentScene != null;
        scene = currentScene;
    }

    @Override
    public synchronized void start() {
        while (true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (x > 100) {
                x = 0;
            }
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
