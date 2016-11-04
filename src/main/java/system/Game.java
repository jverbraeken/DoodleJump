package system;

import input.IInputManager;
import logging.ILogger;
import math.ICalc;
import objects.blocks.BlockTypes;
import resources.sprites.SpriteFactory;
import scenes.IScene;
import scenes.Popup;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static system.Game.Modes.regular;
import static system.Game.PlayerModes.single;

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
     * A LOCK to avoid threading issues.
     */
    private static final transient Object LOCK = new Object();

    /**
     * Used to gain access to all services.
     */
    private static volatile IServiceLocator serviceLocator;
    /**
     * The current frame.
     */
    private static JFrame frame;
    /**
     * The logger for the Game class.
     */
    private static ILogger logger;
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
     * A {@link Queue} of popups.
     */
    private static final Set<Popup> activePopups = new HashSet<>();
    /**
     * The enums for the mode.
     */
    public enum Modes {
        /**
         * The regular game mode.
         */
        regular(0),
        /**
         * The game mode taking place underwater.
         */
        underwater(2),
        /**
         * The game mode following a horizontalOnly platforms.
         */
        horizontalOnly(1),
        /**
         * The game using the vertical platforms.
         */
        verticalOnly(5),
        /**
         * The game mode with invisible platforms.
         * The platforms turn visible when touched by a doodle.
         */
        darkness(4),
        /**
         * The game mode taking place in space.
         */
        space(3),
        /**
         * A default mode, same as regular.
         * Now only used for testing.
         */
        defaultmode(-1);
        /**
         * The rank required to play this mode.
         */
        private final int rankRequired;
        /**
         * Creates an instance of the enum Modes.
         * @param rankRequired the rank required to play this mode.
         */
         /* package */ Modes(final int rankRequired) {
            this.rankRequired = rankRequired;
        }

        /**
         * Returns the variable rankRequired.
         * @return the variable rankRequired.
         */
        public int getRankRequired() {
            return rankRequired;
        }
    }
    /**
     * Track the current mode of the game.
     */
    private static Modes mode = regular;
    /**
     * The enums for the player mode.
     */
    public enum PlayerModes {
        /**
         * The single player mode.
         */
        single,
        /**
         * The multi-player mode.
         */
        multi
    }
    /**
     * Track the current playerMode of the game.
     */
    private static PlayerModes playerMode = single;
    /**
     * The scale of the game.
     */
    private static float scale = 2;
    /**
     * The pause screen for the game.
     */
    private static IScene pauseScreen;
    /**
     * Runnables scheduled to run during the next iteration.
     */
    private static volatile Queue<Runnable> runnables = new LinkedList<>();
    /**
     * Use a double buffer to prevent ConcurrentModificationErrors.
     */
    private static volatile Queue<Runnable> addToRunnables = new LinkedList<>();

    /**
     * Used by Cucumber test.
     */
    private Game() {
        if (Game.serviceLocator == null) {
            synchronized (this) {
                if (Game.serviceLocator == null) {
                    Game.serviceLocator = ServiceLocatorNoAudio.getServiceLocator();
                }
            }
            Game.logger = Game.serviceLocator.getLoggerFactory().createLogger(Game.class);
        }
    }

    /**
     * Prevents instantiation from outside the Game class.
     *
     * @param sL the ServiceLocator of this game.
     */
    private Game(final IServiceLocator sL) {
        if (Game.serviceLocator == null) {
            Game.serviceLocator = sL;
        }
        Game.logger = Game.serviceLocator.getLoggerFactory().createLogger(Game.class);
    }

    /**
     * The initialization of the game.
     *
     * @param argv the arguments to run.
     */
    public static void main(final String[] argv) {
        new Game(ServiceLocator.getServiceLocator());
        Game.logger.info("The game has been launched");
        Game.serviceLocator.getProgressionManager().init();
        Game.pauseScreen = Game.serviceLocator.getSceneFactory().createPauseScreen();
        IInputManager inputManager = Game.serviceLocator.getInputManager();

        // Initialize frame
        Game.frame = new JFrame("Doodle Jump");
        Game.frame.addMouseListener(inputManager);
        Game.frame.addKeyListener(inputManager);
        Game.frame.setSize(Game.serviceLocator.getConstants().getGameWidth(),
                Game.serviceLocator.getConstants().getGameHeight());
        Game.frame.setVisible(true);
        Game.frame.setResizable(false);
        Game.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Game.frame.setSize(Game.serviceLocator.getConstants().getGameWidth() / 2,
                Game.serviceLocator.getConstants().getGameHeight() / 2);
        Game.frame.addWindowListener(new WindowAdapter() {

            /**
             * Invoked when a window is in the process of being closed.
             */
            public void windowClosing(final WindowEvent windowEvent) {
                System.exit(0);
            }

        });

        // Initialize panel
        Game.panel = new JPanel() {
            /**
             * Paint the component to the proper scale.
             *
             * @param g the graphics.
             */
            @Override
            public void paintComponent(final Graphics g) {
                Game.serviceLocator.getRenderer().setGraphicsBuffer(g);

                ((Graphics2D) g).scale(1 / scale, 1 / scale);
                if (Game.scene != null) {
                    Game.scene.render();
                }

                if (Game.isPaused) {
                    Game.pauseScreen.render();
                }

                Game.activePopups.forEach(Popup::render);
                ((Graphics2D) g).scale(Game.scale, Game.scale);
            }
        };

        Game.panel.setLayout(new GridLayout(1, 1));
        Game.frame.setContentPane(Game.panel);

        Game.setScene(Game.serviceLocator.getSceneFactory().createMainMenu());
        int x = (int) (Game.panel.getLocationOnScreen().getX() - Game.frame.getLocationOnScreen().getX());
        int y = (int) (Game.panel.getLocationOnScreen().getY() - Game.frame.getLocationOnScreen().getY());
        Game.serviceLocator.getInputManager().setMainWindowBorderSize(x, y);

        Game.start();
    }

    /**
     * Starts the "engine", the thread that redraws the interface at set
     * intervals.
     */
    public static void start() {
        Game.frame.setVisible(true);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(Game::loop, 0, Game.FRAME_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * Return the current mode.
     *
     * @return the mode.
     */
    public static Modes getMode() {
        return Game.mode;
    }

    /**
     * Set the mode of the Game.
     *
     * @param m The mode to set.
     */
    public static void setMode(final Modes m) {
        Game.mode = m;
        Game.serviceLocator.getRes().setSkin(m);
        SpriteFactory.register(Game.serviceLocator);
        setScene(Game.serviceLocator.getSceneFactory().newChooseMode());
        BlockTypes.setMode(m);
        Game.logger.info("The mode is now " + m);
    }

    /**
     * Get the current playerMode.
     *
     * @return the player mode of the player.
     */
    public static PlayerModes getPlayerMode() {
        return Game.playerMode;
    }

    /**
     * Set the current playerMode.
     *
     * @param m the player mode the player has to be set on.
     */
    public static void setPlayerMode(final PlayerModes m) {
        Game.playerMode = m;
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
            double delta = updateLength / ((double) Game.OPTIMAL_TIME);

            lastFpsTime += updateLength;
            if (lastFpsTime >= ICalc.NANOSECONDS) {
                lastFpsTime = 0;
            }

            // Add new runnables
            while (Game.addToRunnables.size() > 0) {
                Game.runnables.add(Game.addToRunnables.remove());
            }
            // Run runnables
            while (Game.runnables.size() > 0) {
                (Game.runnables.remove()).run();
            }

            // Render the pause screen if necessary, otherwise render the normal scene
            if (Game.isPaused) {
                Game.pauseScreen.update(delta);
            } else {
                Game.scene.update(delta);
                Game.serviceLocator.getProgressionManager().update();
            }

            // Render the frame
            Game.panel.repaint();

            // Sleep until the next frame
            try {
                Thread.sleep(Game.FRAME_TIME - (now - System.nanoTime()) / ICalc.NANOSECONDS);
            } catch (InterruptedException e) {
                Game.logger.error(e);
            }

            Game.logger.info("FPS is " + Game.getFPS(updateLength, 0));
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
            synchronized (Game.LOCK) {
                Game.startScene(scene);
            }
        } else {
            Game.scene.stop();
            Game.startScene(scene);
        }
    }

    /**
     * Private helper method that starts a new {@link IScene scene}.
     *
     * @param scene The scene that must be started
     */
    private static void startScene(final IScene scene) {
        assert scene != null;
        Game.serviceLocator.getRenderer().getCamera().setYPos(0d);
        Game.scene = scene;
        Game.scene.start();
    }

    /**
     * Use a buffer to prevent ConcurrentModificationExceptions.
     *
     * @param runnable The runnable to be executed during the next run
     */
    public static void schedule(final Runnable runnable) {
        Game.addToRunnables.add(runnable);
    }

    /**
     * Pause the game.
     */
    public static void pauseGame() {
        Game.logger.info("The game has been paused");
        Game.pauseScreen.start();
        Game.isPaused = true;
    }

    /**
     * Resume the game.
     */
    public static void resumeGame() {
        Game.logger.info("The game has been resumed");
        Game.pauseScreen.stop();
        Game.isPaused = false;
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
            return Game.TARGET_FPS;
        }
        return (double) ICalc.NANOSECONDS / (double) (threadSleep + renderTime);
    }

    /**
     * Returns the pause screen.
     *
     * @return IScene object
     */
    public static IScene getPauseScreen() {
        return Game.pauseScreen;
    }

    /**
     * Returns the current scene.
     *
     * @return IScene object
     */
    public static IScene getScene() {
        return Game.scene;
    }

    /**
     * Add a Popup to the activePopups.
     *
     * @param popup the Popup that has to be added.
     */
    public static void addPopup(final Popup popup) {
        Game.activePopups.add(popup);
    }

    /**
     * Deletes a Popup from the activePopups.
     *
     * @param popup the Popup that has to be deleted.
     */
    public static void deletePopup(final Popup popup) {
        Game.activePopups.remove(popup);
    }
}
