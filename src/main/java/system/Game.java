package system;

import buttons.IButton;
import input.IInputManager;
import logging.ILogger;
import math.ICalc;
import scenes.IScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This is the main class that runs the game.
 */
public final class Game {

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator sL = new ServiceLocator();

    /**
     * The time in miliseconds per frame.
     */
    private static final int FRAME_TIME = 16;
    /**
     * The logger for the Game class.
     */
    private static final ILogger LOGGER = sL.getLoggerFactory().createLogger(Game.class);
    /**
     * The target FPS for the game.
     */
    private static final int TARGET_FPS = 60;
    /**
     * The optimal time per frame. ~16.
     */
    private static final long OPTIMAL_TIME = ICalc.NANOSECONDS / TARGET_FPS;
    /**
     * X position relative to the frame of the resume button.
     */
    private static final double RESUME_BUTTON_X = 0.55;
    /**
     * Y position relative to the frame of the resume button.
     */
    private static final double RESUME_BUTTON_Y = 0.75;

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
     * The pause screen for the game.
     */
    private static IScene pauseScreen;

    /**
     * Prevents instantiation from outside the Game class.
     */
    private Game() {
    }

    /**
     * The initialization of the game.
     *
     * @param argv the arguments to run.
     */
    public static void main(String[] argv) {
        LOGGER.info("The game has been launched");

        Game.pauseScreen = sL.getSceneFactory().createPauseScreen();
        IInputManager inputManager = sL.getInputManager();
        sL.getRenderer().start();

        // Initialize frame
        frame = new JFrame("Doodle Jump");
        frame.addMouseListener(inputManager);
        frame.addKeyListener(inputManager);
        frame.setSize(sL.getConstants().getGameWidth(), sL.getConstants().getGameHeight());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(sL.getConstants().getGameWidth() / 2, sL.getConstants().getGameHeight() / 2);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             */
            public void windowClosing(final WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Initialize panel
        panel = new JPanel() {
            /**
             * TODO: Add JavaDoc
             */
            @Override
            public void paintComponent(final Graphics g) {
            sL.getRenderer().setGraphicsBuffer(g);

            ((Graphics2D) g).scale(1 / scale, 1 / scale);
            if (Game.scene != null) {
                Game.scene.render();
            }

            if (isPaused) {
                pauseScreen.render();
            }

            ((Graphics2D) g).scale(scale, scale);
            }
        };
        panel.setLayout(new GridLayout(1, 1));
        frame.setContentPane(panel);

        setScene(sL.getSceneFactory().createMainMenu());
        int x = (int) (panel.getLocationOnScreen().getX() - frame.getLocationOnScreen().getX());
        int y = (int) (panel.getLocationOnScreen().getY() - frame.getLocationOnScreen().getY());
        sL.getInputManager().setMainWindowBorderSize(x, y);

        resumeButton = sL.getButtonFactory().createResumeButton((int) (sL.getConstants().getGameWidth() * RESUME_BUTTON_X), (int) (sL.getConstants().getGameHeight() * RESUME_BUTTON_Y));
        sL.getInputManager().addObserver(resumeButton);

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

        sL.getRenderer().getCamera().setYPos(0d);
        s.start();
        Game.scene = s;
    }

    /**
     * Returns the current FPS.
     *
     * @param threadSleep Amount of time thread has slept
     * @param renderTime  Amount of time took rendering/updating
     * @return The current Frames Per Second (FPS)
     */
    public static double getFPS(final long threadSleep, final long renderTime) {
        return sL.getCalc().NANOSECONDS / (threadSleep + renderTime);
    }

    /**
     * Pauses or resumes the game.
     *
     * @param paused <b>True</b> if the game must be paused, <b>false</b> if the game must be resumed
     */
    public static void setPaused(final boolean paused) {
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
    public static void startGame(final boolean alive) {
        if (!alive) {
            LOGGER.info("The Doodle died");
        }

        isAlive = alive;
    }

    public static void endGame(final double score) {
        setScene(sL.getSceneFactory().createKillScreen());
    }

    /**
     * Set the state of the game to be alive or dead and provide a score.
     *
     * @param alive <b>True</b> if the game must be paused, <b>false</b> if the game must be resumed
     */

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
                long gameTime = FRAME_TIME;
                Thread.sleep(gameTime - (now - System.nanoTime()) / ICalc.NANOSECONDS);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
    }

}