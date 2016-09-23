package system;

import constants.Constants;
import constants.IConstants;
import filesystem.FileSystem;
import input.IInputManager;
import input.InputManager;
import logging.LoggerFactory;
import math.Calc;
import math.ICalc;
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
    private static final IServiceLocator sL = new ServiceLocator();
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
        FileSystem.register(sL);
        LoggerFactory.register(sL);
        AudioManager.register(sL);
        EnemyBuilder.register(sL);
        InputManager.register(sL);
        Calc.register(sL);
        BlockFactory.register(sL);
        DoodleFactory.register(sL);
        PowerupFactory.register(sL);
        SpriteFactory.register(sL);
        Renderer.register(sL);
        SceneFactory.register(sL);
        PlatformFactory.register(sL);
        Res.register(sL);
        ButtonFactory.register(sL);
        Constants.register(sL);
    }

    /**
     * The initialization of the game.
     * @param argv the arguments to run.
     */
    public static void main(final String[] argv) {
        initServices();

        sL.getRenderer().start();

        frame = new JFrame("Doodle Jump");

        IInputManager inputManager = sL.getInputManager();

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
        frame.setSize(sL.getConstants().getGameWidth(), sL.getConstants().getGameHeight());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(final Graphics g) {
                sL.getRenderer().setGraphicsBuffer(g);

                ((Graphics2D) g).scale(1 / scale, 1 / scale);
                if (Game.scene != null) {
                    Game.scene.render();
                }


                if (isPaused) {
                    drawPauseScreen();
                }

                if (!isAlive) {
                    setScene(sL.getSceneFactory().newKillScreen());
                    setAlive(true);
                }

                ((Graphics2D) g).scale(scale, scale);
            }
        };
        frame.setSize(sL.getConstants().getGameWidth() / 2, sL.getConstants().getGameHeight() / 2);
        panel.setLayout(new GridLayout(1, 1));

        frame.setContentPane(panel);

        setScene(sL.getSceneFactory().newMenu());
        int x = (int) (panel.getLocationOnScreen().getX() - frame.getLocationOnScreen().getX());
        int y = (int) (panel.getLocationOnScreen().getY() - frame.getLocationOnScreen().getY());
        sL.getInputManager().setMainWindowBorderSize(x, y);

        resumeButton = sL.getButtonFactory().createResumeButton((int) (sL.getConstants().getGameWidth() * RESUMEBUTTONX), (int) (sL.getConstants().getGameHeight() * RESUMEBUTTONY));
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
                Thread.sleep(gameTime - (now - System.nanoTime()) / ICalc.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Draw the pause screen.
     */
    private static void drawPauseScreen() {
        ISprite pauseCover = sL.getSpriteFactory().getPauseCoverSprite();
        double scaling = (double) sL.getConstants().getGameWidth() / (double) pauseCover.getWidth();
        sL.getRenderer().drawSpriteHUD(pauseCover, 0, 0, (int) (pauseCover.getWidth() * scaling), (int) (pauseCover.getHeight() * scaling));
        resumeButton.render();
    }

}