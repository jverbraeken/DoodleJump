package system;

import input.IInputManager;
import math.ICalc;
import objects.Collisions;
import objects.backgrounds.BackgroundFactory;
import objects.buttons.ButtonFactory;
import resources.Res;
import resources.audio.AudioManager;
import filesystem.FileSystem;
import input.InputManager;
import math.Calc;
import objects.blocks.BlockFactory;
import objects.doodles.DoodleFactory;
import objects.enemies.EnemyBuilder;
import objects.blocks.platform.PlatformFactory;
import objects.powerups.PowerupFactory;
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
    
    public final static int WIDTH = 400;
    public final static int HEIGHT = 800;

    private static JFrame frame;
    private static JPanel panel;

    private static IScene scene;

    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = ICalc.NANOSECONDS / TARGET_FPS;

    private static int times = 0;

    public static final int NORMAL_WIDTH = Game.WIDTH;
    public static final int NORMAL_HEIGHT = Game.HEIGHT;
    private static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private static float scale =   Math.min( (float)d.getWidth() / NORMAL_WIDTH, (float)d.getHeight() / NORMAL_HEIGHT );

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
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

        initServices();

        serviceLocator.getRenderer().start();

        frame = new JFrame("Doodle Jump");

        IInputManager inputManager = serviceLocator.getInputManager();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            /**
             * Invoked when a window is in the process of being closed.
             */
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        frame.addMouseListener(inputManager);
        frame.addKeyListener(inputManager);
        frame.setSize(Game.WIDTH, Game.HEIGHT);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                serviceLocator.getRenderer().setGraphicsBuffer(g);

                ((Graphics2D)g).scale(1/scale,1/scale);

                if (scene != null) {
                    scene.paint();
                }

                if(times % 2 == 0) {
                    frame.repaint();
                }
                times ++;

                ((Graphics2D)g).scale(scale,scale);
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