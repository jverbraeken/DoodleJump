package system;

import math.ICalc;
import objects.Collisions;
import objects.backgrounds.BackgroundFactory;
import objects.buttons.ButtonFactory;
import resources.Res;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import filesystem.FileSystem;
import input.InputManager;
import math.Calc;
import objects.blocks.BlockFactory;
import objects.doodles.DoodleFactory;
import objects.enemies.EnemyBuilder;
import objects.blocks.platform.PlatformFactory;
import objects.powerups.PowerupFactory;
import rendering.IRenderer;
import rendering.Renderer;
import scenes.IScene;
import scenes.SceneFactory;
import resources.sprites.SpriteFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class Game {

    private static IServiceLocator serviceLocator = new ServiceLocator();
    
    public final static int WIDTH = 500;
    public final static int HEIGHT = 800;

    private static JFrame frame;
    private static JPanel panel;

    private static IScene scene;

    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = ICalc.NANOSECONDS / TARGET_FPS;

    private static int times = 0;

    private static void initServices() {
        AudioManager.register(serviceLocator);
        EnemyBuilder.register(serviceLocator);
        FileSystem.register(serviceLocator);
        InputManager.register(serviceLocator);
        Calc.register(serviceLocator);
        BlockFactory.register(serviceLocator);
        DoodleFactory.register(serviceLocator);
        PowerupFactory.register(serviceLocator);
        SpriteFactory.register(serviceLocator);
        Renderer.register(serviceLocator);
        SceneFactory.register(serviceLocator);
        PlatformFactory.register(serviceLocator);
        Res.register(serviceLocator);
        ButtonFactory.register(serviceLocator);
        BackgroundFactory.register(serviceLocator);
        Collisions.register(serviceLocator);
    }

    /**
     * Prevents the creation of a new {@code Game} object.
     */
    private Game() {

    }

    public static void main(String[] argv) {
        initServices();

        serviceLocator.getRenderer().start();

        frame = new JFrame("Doodle Jump");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            /**
             * Invoked when a window is in the process of being closed.
             */
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        frame.addMouseListener(serviceLocator.getInputManager());
        frame.setSize(Game.WIDTH, Game.HEIGHT);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                serviceLocator.getRenderer().setGraphicsBuffer(g);
                if (scene != null) {
                    scene.paint();
                }

                if(times % 2 == 0){
                    frame.repaint();
                }
                times ++;
            }
        };
        panel.setSize(Game.WIDTH, Game.HEIGHT);
        panel.setLayout(new GridLayout(1, 1));

        frame.setContentPane(panel);

        setScene(serviceLocator.getSceneFactory().newMenu());

        loop();
    }

    /**
     * Sets the current scene to currentScene
     * @param scene The new scene that must be visible to the user. Cannot be null
     */
    public static void setScene(IScene scene) {
        assert scene != null;
        if (Game.scene != null) {
            Game.scene.stop();
        }
        scene.start();
        Game.scene = scene;
        frame.repaint();
    }

    private static synchronized void loop() {

        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0;
        while (true) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            if (lastFpsTime >= ICalc.NANOSECONDS) {
                lastFpsTime = 0;
            }
            scene.update(delta);
            panel.repaint();
            try {
                long gameTime = 16;
                        //= (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / ICalc.MICROSCONDS;
                //System.out.println(gameTime);
                Thread.sleep(gameTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the current FPS
     *
     * @param threadSleep Amount of time thread has slept
     * @param renderTime  Amount of time took rendering/updating
     * @return The current Frames Per Second (FPS)
     */
    public static double getFPS(long threadSleep, long renderTime) {
        return 1000000000 / (threadSleep + renderTime);
    }
}