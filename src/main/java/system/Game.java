package system;

import filesystem.FileSystem;
import input.IInputManager;
import input.InputManager;
import logging.ILogger;
import logging.LoggerFactory;
import math.Calc;
import math.ICalc;
import objects.Collisions;
import objects.blocks.BlockFactory;
import objects.blocks.platform.PlatformFactory;
import buttons.ButtonFactory;
import buttons.IButton;
import objects.doodles.DoodleFactory;
import objects.doodles.IDoodle;
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
    private static ILogger LOGGER;
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
    private static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private static float scale = 2;
    /**
     * The pause screen for the game.
     */
    private static IScene pauseScreen;

    /**
     * Prevents the creation of a new {@code Game} object.
     */
    private Game() {
    }

    public static void main(String[] argv) {
        Game.LOGGER = serviceLocator.getLoggerFactory().createLogger(Game.class);

        Game.pauseScreen = serviceLocator.getSceneFactory().createPauseScreen();
        IInputManager inputManager = serviceLocator.getInputManager();
        serviceLocator.getRenderer().start();

        // Initialize frame
        frame = new JFrame("Doodle Jump");
        frame.addMouseListener(inputManager);
        frame.addKeyListener(inputManager);
        frame.setSize(Game.WIDTH, Game.HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Game.WIDTH / 2, Game.HEIGHT / 2);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             */
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Initialize panel
        panel = new JPanel() {
            /**
             * TODO: Add JavaDoc
             */
            @Override
            public void paintComponent(Graphics g) {
                serviceLocator.getRenderer().setGraphicsBuffer(g);

                ((Graphics2D) g).scale(1 / scale, 1 / scale);
                if (scene != null) {
                    scene.paint();
                }

                if (isPaused) {
                    pauseScreen.paint();
                }

                if(!isAlive) {
                    setScene(serviceLocator.getSceneFactory().newKillScreen());
                    setAlive(true);
                }

                ((Graphics2D) g).scale(scale, scale);
            }
        };
        panel.setLayout(new GridLayout(1, 1));
        frame.setContentPane(panel);

        setScene(serviceLocator.getSceneFactory().newMenu());
        int xOffset = (int) (panel.getLocationOnScreen().getX() - frame.getLocationOnScreen().getX());
        int yOffset = (int) (panel.getLocationOnScreen().getY() - frame.getLocationOnScreen().getY());
        serviceLocator.getInputManager().setMainWindowBorderSize(xOffset, yOffset);

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
        if (paused) {
            LOGGER.info("The game has been paused");
            pauseScreen.start();
        } else {
            LOGGER.info("The game has been resumed");
            pauseScreen.stop();
        }

        isPaused = paused;
    }

    /**
     * Set the state of the game to be alive or dead.
     *
     * @param alive <b>True</b> if the game must be paused, <b>false</b> if the game must be resumed
     */
    public static void setAlive(boolean alive) {
        if (!alive) {
            LOGGER.info("The Doodle died");
        }

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
                LOGGER.error(e);
            }
        }
    }

}
