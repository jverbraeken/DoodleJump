package system;

import math.ICalc;
import objects.backgrounds.BackgroundFactory;
import objects.buttons.ButtonFactory;
import resources.Res;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import filesystem.FileSystem;
import input.InputManager;
import math.Calc;
import objects.BlockFactory;
import objects.doodles.DoodleFactory;
import objects.enemies.EnemyBuilder;
import objects.platform.PlatformFactory;
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
    public final static int width = 640;
    public final static int height = 1024;

    private static JFrame frame;
    private static JPanel panel;

    private static IScene scene;

    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = ICalc.NANOSECONDS / TARGET_FPS;

    private static void initServices() {
        AudioManager.register(serviceLocator);
        EnemyBuilder.register(serviceLocator);
        FileSystem.register(serviceLocator);
        InputManager.register(serviceLocator);
        Calc.register(serviceLocator);
        BlockFactory.register(width, height, serviceLocator);
        DoodleFactory.register(serviceLocator);
        PowerupFactory.register(serviceLocator);
        SpriteFactory.register(serviceLocator);
        Renderer.register(serviceLocator);
        SceneFactory.register(serviceLocator);
        PlatformFactory.register(serviceLocator);
        Res.register(serviceLocator);
        ButtonFactory.register(serviceLocator);
        BackgroundFactory.register(serviceLocator);
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
        frame.setSize(Game.width, Game.height);
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
            }
        };
        panel.setSize(Game.width, Game.height);
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
                long gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / ICalc.MICROSCONDS;
                System.out.println(gameTime);
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