package system;

import filesystem.FileSystem;
import input.IInputManager;
import input.InputManager;
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

/**
 * This is the main class that runs the game.
 */
public final class Game {

    /**
     * The width of the frame of the game.
     */
    public static final int WIDTH = 640;
    /**
     * The height of the frame of the game.
     */
    public static final int HEIGHT = 960;
    /**
     * The target amount of frames per second.
     */
    private static final int TARGET_FPS = 60;
    /**
     * The optimal time per frame. ~16.
     */
    private static final long OPTIMAL_TIME = ICalc.NANOSECONDS / TARGET_FPS;
    /**
     * X position relative to the frame of the resume button.
     */
    private static final double RESUMEBUTTONX = 0.55;
    /**
     * Y position relative to the frame of the resume button.
     */
    private static final double RESUMEBUTTONY = 0.75;
    /**
     * Used to access all services.
     */
    private static IServiceLocator serviceLocator = new ServiceLocator();
    /**
     * The current frame.
     */
    private static JFrame frame;
    /**
     * The current panel.
     */
    private static JPanel panel;
    /**
     * The current scene.
     */
    private static IScene scene;
    /**
     * Track if the game is paused.
     */
    private static boolean isPaused = false;
    /**
     * Track wether the doodle is alive.
     */
    private static boolean isAlive = true;
    /**
     * The resume button for the pause screen.
     */
    private static IButton resumeButton;
    /**
     * The scale of the game.
     */
    private static float scale = 2;
    /**
     * The time in miliseconds per frame.
     */
    private static final int FRAMETIME = 16;

    /**
     * Prevents the creation of a new {@code Game} object.
     */
    private Game() {
    }

    /**
     * Initialize all services into the service locator.
     */
    private static void initServices() {
        FileSystem.register(serviceLocator); // This ine is before the audiomanager!
        AudioManager.register(serviceLocator);
        EnemyBuilder.register(serviceLocator);
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

    /**
     * The initialization of the game.
     * @param argv the arguments to run.
     */
    public static void main(final String[] argv) {
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
            public void windowClosing(final WindowEvent windowEvent) {
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
            public void paintComponent(final Graphics g) {
                serviceLocator.getRenderer().setGraphicsBuffer(g);

                ((Graphics2D) g).scale(1 / scale, 1 / scale);
                if (scene != null) {
                    scene.paint();
                }


                if (isPaused) {
                    drawPauseScreen();
                }

                if (!isAlive) {
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
     * Sets the current scene to currentScene.
     *
     * @param s The new scene that must be visible to the user. Cannot be null
     */
    public static void setScene(final IScene s) {
        assert s != null;
        if (Game.scene != null) {
            Game.scene.stop();
        }

        Game.scene = s;
        scene.start();
        frame.repaint();
    }

    /**
     * Returns the current FPS.
     *
     * @param threadSleep Amount of time thread has slept
     * @param renderTime  Amount of time took rendering/updating
     * @return The current Frames Per Second (FPS)
     */
    public static double getFPS(final long threadSleep, final long renderTime) {
        return serviceLocator.getCalc().NANOSECONDS / (threadSleep + renderTime);
    }

    /**
     * Pauses or resumes the game.
     *
     * @param paused <b>True</b> if the game must be paused, <b>false</b> if the game must be resumed
     */
    public static void setPaused(final boolean paused) {
        isPaused = paused;
    }

    /**
     * Set the state of the game to be alive or dead.
     *
     * @param alive <b>True</b> if the game must be paused, <b>false</b> if the game must be resumed
     */
    public static void setAlive(final boolean alive) {
        isAlive = alive;
    }


    /**
     * Loop to update the game 60x per second.
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
                long gameTime = FRAMETIME;
                Thread.sleep(gameTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Draw the pause screen.
     */
    private static void drawPauseScreen() {
        ISprite pauseCover = serviceLocator.getSpriteFactory().getPauseCoverSprite();
        double scaling = (double) WIDTH / (double) pauseCover.getWidth();
        serviceLocator.getRenderer().drawSprite(pauseCover, 0, 0, (int) (pauseCover.getWidth() * scaling), (int) (pauseCover.getHeight() * scaling));
        resumeButton.render();
    }

}
