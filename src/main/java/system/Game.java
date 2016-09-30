package system;

import input.IInputManager;
import logging.ILogger;
import math.ICalc;
import resources.sprites.SpriteFactory;
import scenes.IScene;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;

import static system.Game.Modes.regular;

/**
 * This is the main class that runs the game.
 */
public final class Game {

    /**
     * Indicates if the log file should be cleared each time the game starts.
     * This constant is not provided by an implementation of {@link constants.IConstants} because
     * such an implementation will normally use the FileSystem which is for that reason initialised earlier, but
     * does need to know whether is should clear the log file on startup or not.
     */
    public static final boolean CLEAR_LOG_ON_STARTUP = true;
    /**
     * The filepath to the logfile to which all logs will be written to.
     * This constant is not provided by an implementation of {@link constants.IConstants} because
     * such an implementation will normally use the FileSystem which is for that reason initialised earlier, but
     * does need the name of the log file.
     */
    public static final String LOGFILE_NAME = "async.log";
    /**
     * The time in milliseconds per frame.
     */
    private static final int FRAME_TIME = 16;
    /**
     * The target FPS for the game.
     */
    private static final int TARGET_FPS = 60;
    /**
     * The optimal time per frame.
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
     * The maximum size of the list of high scores.
     */
    private static final int MAX_HIGH_SCORES = 10;
    /**
     * A LOCK to avoid threading issues.
     */
    private static final transient Object LOCK = new Object();
    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator = new ServiceLocator();
    /**
     * The logger for the Game class.
     */
    private static final ILogger LOGGER = serviceLocator.getLoggerFactory().createLogger(Game.class);
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
     * Track the current mode of the game.
     */
    private static Modes mode = regular;
    /**
     * The scale of the game.
     */
    private static float scale = 2;
    /**
     * The pause screen for the game.
     */
    private static IScene pauseScreen;
    /**
     * A list of high scores for the game.
     */
    private static ArrayList<HighScore> highScores = new ArrayList<>();

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
    public static void main(final String[] argv) {
        LOGGER.info("The game has been launched");

        Game.pauseScreen = serviceLocator.getSceneFactory().createPauseScreen();
        IInputManager inputManager = serviceLocator.getInputManager();

        // Initialize frame
        frame = new JFrame("Doodle Jump");
        frame.addMouseListener(inputManager);
        frame.addKeyListener(inputManager);
        frame.setSize(serviceLocator.getConstants().getGameWidth(), serviceLocator.getConstants().getGameHeight());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(serviceLocator.getConstants().getGameWidth() / 2, serviceLocator.getConstants().getGameHeight() / 2);
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
            /** {@inheritDoc} */
            @Override
            public void paintComponent(final Graphics g) {
                serviceLocator.getRenderer().setGraphicsBuffer(g);

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

        setScene(serviceLocator.getSceneFactory().createMainMenu());
        int x = (int) (panel.getLocationOnScreen().getX() - frame.getLocationOnScreen().getX());
        int y = (int) (panel.getLocationOnScreen().getY() - frame.getLocationOnScreen().getY());
        serviceLocator.getInputManager().setMainWindowBorderSize(x, y);

        loop();
    }

    /**
     * End the game.
     *
     * @param score The score the game instance ended with.
     */
    public static void endGameInstance(final double score) {
        updateHighScores(score);
        setScene(serviceLocator.getSceneFactory().createKillScreen());
    }

    /**
     * Return the current mode.
     *
     * @return the mode.
     */
    public static Modes getMode() {
        return mode;
    }

    /**
     * Set the mode of the Game.
     *
     * @param m The mode to set.
     */
    public static void setMode(final Modes m) {
        mode = m;
        serviceLocator.getRes().setSkin(m);
        SpriteFactory.register(serviceLocator);
        LOGGER.info("The mode is now " + m);
    }

    /**
     * Loop to update the game 60x per second.
     */
    private static void loop() {
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
                Thread.sleep(FRAME_TIME - (now - System.nanoTime()) / ICalc.NANOSECONDS);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }

            LOGGER.info("FPS is " + getFPS(updateLength, 0));
        }
    }

    /**
     * Sets the current scene to {@code scene}.
     *
     * @param scene The new scene that must be visible to the user. Cannot be null
     */
    public static void setScene(final IScene scene) {
        assert scene != null;
        if (Game.scene == null) {
            synchronized (LOCK) {
                if (Game.scene == null) {
                    startScene(scene);
                }
            }
        } else {
            Game.scene.stop();
            startScene(scene);
        }
    }

    /**
     * Private helper method that starts a new {@link IScene scene}.
     *
     * @param scene The scene that must be started
     */
    private static void startScene(final IScene scene) {
        assert scene != null;
        serviceLocator.getRenderer().getCamera().setYPos(0d);
        scene.start();
        Game.scene = scene;
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
     * Update the high scores for the game.
     *
     * @param score The score the game instance ended with.
     */
    private static void updateHighScores(final double score) {
        HighScore scoreEntry = new HighScore("", score);
        Game.highScores.add(scoreEntry);
        Collections.sort(Game.highScores);

        for (int i = Game.highScores.size(); i > MAX_HIGH_SCORES; i--) {
            Game.highScores.remove(i - 1);
        }
    }

    /**
     * Returns the current FPS.
     *
     * @param threadSleep Amount of time thread has slept
     * @param renderTime  Amount of time took rendering/updating
     * @return The current Frames Per Second (FPS)
     */
    private static double getFPS(final long threadSleep, final long renderTime) {
        if (threadSleep + renderTime == 0) {
            return TARGET_FPS;
        }
        return (double) ICalc.NANOSECONDS / (double) (threadSleep + renderTime);
    }

    /**
     * The enums for the mode.
     */
    public enum Modes {
        /**
         * As usual.
         */
        regular,
        /**
         * Underwater -> slow moving.
         */
        underwater,
        /**
         * Accompanied with a story.
         */
        story,
        /**
         * Everything upside-down.
         */
        invert,
        /**
         * You don't see any platforms except for the ones you jumped on.
         */
        darkness,
        /**
         * Fast acceleration, slow deceleration.
         */
        space
    }

}
