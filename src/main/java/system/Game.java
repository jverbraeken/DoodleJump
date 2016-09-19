package system;

import filesystem.FileSystem;
import input.IInputManager;
import input.InputManager;
import logging.Console;
import math.Calc;
import math.ICalc;
import objects.Collisions;
import objects.blocks.BlockFactory;
import objects.blocks.platform.PlatformFactory;
import objects.buttons.ButtonFactory;
import objects.buttons.IButton;
import objects.doodles.DoodleFactory;
import objects.enemies.EnemyBuilder;
import objects.powerups.PowerupFactory;
import rendering.Renderer;
import resources.Res;
import resources.audio.AudioManager;
import resources.sprites.ISprite;
import resources.sprites.SpriteFactory;
import scenes.IScene;
import scenes.SceneFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class Game {

    // TODO: Remove unused and add JavaDoc
    public final static int WIDTH = 640;
    public final static int HEIGHT = 960;
    public static final int NORMAL_WIDTH = Game.WIDTH;
    public static final int NORMAL_HEIGHT = Game.HEIGHT;
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = ICalc.NANOSECONDS / TARGET_FPS;
    private static final double RESUMEBUTTONX = 0.55;
    private static final double RESUMEBUTTONY = 0.75;
    private static IServiceLocator serviceLocator = new ServiceLocator();
    private static JFrame frame;
    private static JPanel panel;
    private static IScene scene;
    private static int times = 0;
    private static boolean isPaused = false;
    private static boolean isAlive = true;
    private static IButton resumeButton;
    private static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private static float scale = 2;

    /**
     * Prevents the creation of a new {@code Game} object.
     */
    private Game() {
    }

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
        Collisions.register(serviceLocator);
    }

    public static void main(String[] argv) {
        Console.log("Game started");

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
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                serviceLocator.getRenderer().setGraphicsBuffer(g);

                ((Graphics2D) g).scale(1 / scale, 1 / scale);
                if (scene != null) {
                    scene.paint();
                }


                if (isPaused) {
                    drawPauseScreen();
                }

                if(!isAlive) {
                    setScene(serviceLocator.getSceneFactory().newKillScreen());
                    setAlive(true);
                }

                ((Graphics2D) g).scale(scale, scale);
            }
        };
        frame.setSize(Game.WIDTH / 2, Game.HEIGHT / 2);
        panel.setLayout(new GridLayout(1, 1));

        frame.setContentPane(panel);

        setScene(serviceLocator.getSceneFactory().newMenu());
        int x = (int) (panel.getLocationOnScreen().getX() - frame.getLocationOnScreen().getX());
        int y = (int) (panel.getLocationOnScreen().getY() - frame.getLocationOnScreen().getY());
        serviceLocator.getInputManager().setMainWindowBorderSize(x, y);

        resumeButton = serviceLocator.getButtonFactory().createResumeButton((int) (Game.WIDTH * RESUMEBUTTONX), (int) (Game.HEIGHT * RESUMEBUTTONY));
        serviceLocator.getInputManager().addObserver(resumeButton);

        loop();
    }

    /**
     * Sets the current scene to currentScene
     *
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

    /**
     * Pauses or resumes the game.
     *
     * @param paused <b>True</b> if the game must be paused, <b>false</b> if the game must be resumed
     */
    public static void setPaused(boolean paused) {
        isPaused = paused;
    }

    /**
     * Set the state of the game to be alive or dead.
     *
     * @param alive <b>True</b> if the game must be paused, <b>false</b> if the game must be resumed
     */
    public static void setAlive(boolean alive) {
        isAlive = alive;
    }


    /**
     * TODO: Add JavaDoc
     */
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
            if (!isPaused) {
                scene.update(delta);
            }

            panel.repaint();
            try {
                long gameTime = 16;
                Thread.sleep(gameTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * TODO: Add JavaDoc
     */
    private static void drawPauseScreen() {
        ISprite pauseCover = serviceLocator.getSpriteFactory().getPauseCoverSprite();
        double scaling = (double) WIDTH / (double) pauseCover.getWidth();
        serviceLocator.getRenderer().drawSprite(pauseCover, 0, 0, (int) (pauseCover.getWidth() * scaling), (int) (pauseCover.getHeight() * scaling));
        resumeButton.render();
    }

}
