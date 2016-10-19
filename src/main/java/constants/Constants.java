package constants;

import filesystem.IFileSystem;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class containing the main constants of the game.
 */
public final class Constants implements IConstants {

    /**
     * True if the number of pending tasks of the logging thread executor should be logged.
     */
    private static final boolean LOG_PENDING_TASKS = true;
    /**
     * The width of the frame of the game.
     */
    private static final int WIDTH = 640;
    /**
     * The height of the frame of the game.
     */
    private static final int HEIGHT = 960;
    /**
     * How much the doodle is affected by gravity.
     */
    private static final double GRAVITY_ACCELERATION = 0.5d;
    /**
     * The height the Doodle jumps will be multiplied with this value to obtain the score that the player gets.
     */
    private static final double SCORE_MULTIPLIER = 0.15;
    /**
     * The file to which the high scores will be written to.
     */
    private static final String SAVEFILE_DATA = "saveFile.data";

    /**
     * The service locator for the Constants class.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The file to which the logs will be written to.
     */
    private static final AtomicReference<String> logFile = new AtomicReference<>("async.log");

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     * @param sL The IServiceLocator to which the class should offer its functionality.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Constants.serviceLocator = sL;
        Constants.serviceLocator.provide(new Constants());
    }

    /**
     * Prevent public instantiation of Constants.
     */
    private Constants() {
        /*try {
            IFileSystem fileSystem = serviceLocator.getFileSystem();
            Object jsonObject = fileSystem.parseJson("constants.json", Map.class);
            Map<String, String> json = (Map<String, String>) jsonObject;
            interpretJson(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGameWidth() {
        return Constants.WIDTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGameHeight() {
        return Constants.HEIGHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getGravityAcceleration() {
        return Constants.GRAVITY_ACCELERATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getScoreMultiplier() {
        return Constants.SCORE_MULTIPLIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLogPendingTasks() {
        return Constants.LOG_PENDING_TASKS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogFile() {
        return Constants.logFile.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSaveFilePath() {
        return Constants.SAVEFILE_DATA;
    }

    /**
     * Interpret JSON.
     *
     * @param json The JSON to interpret.
     */
    private void interpretJson(final Map<String, String> json) {
        for (Map.Entry<String, String> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "logFile":
                    Constants.logFile.set(entry.getValue());
                    break;
                default:
                    String msg = "The json entry \"" + entry.getKey()
                            + "\" in the configuration file could not be identified";
                    System.err.print(msg);
            }
        }
    }

}
