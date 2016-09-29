package constants;

import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * This class contains constants for the game.
 */
public class Constants implements IConstants {

    /**
     * True if the number of pending tasks of the logging thread executor should be logged as well.
     */
    private static final boolean LOG_PENDING_TASKS = true;
    /**
     * The WIDTH of the frame of the game.
     */
    private static final int WIDTH = 640;
    /**
     * The height of the frame of the game.
     */
    private static final int height = 960;
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
    private static final String HIGHCORES_DATA = "highScores.data";

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * The file to which the logs will be written to.
     */
    private static String logFile;

    /**
     * Prevent public instantiation of Constants.
     */
    private Constants() {
        try {
            Object jsonObject = serviceLocator.getFileSystem().parseJsonMap("constants.json", String.class);
            Map<String, String> json = (Map<String, String>) jsonObject;
            interpretJson(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers itself to an {@link IServiceLocator} so that other classes can use the services provided by this class.
     *
     * @param sL The IServiceLocator to which the class should offer its functionality.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        Constants.serviceLocator = sL;
        sL.provide(new Constants());
    }

    /** {@inheritDoc} */
    @Override
    public int getGameWidth() {
        return WIDTH;
    }

    /** {@inheritDoc} */
    @Override
    public int getGameHeight() {
        return height;
    }

    /** {@inheritDoc} */
    @Override
    public double getGravityAcceleration() {
        return GRAVITY_ACCELERATION;
    }

    /** {@inheritDoc} */
    @Override
    public double getScoreMultiplier() {
        return SCORE_MULTIPLIER;
    }

    /** {@inheritDoc} */
    @Override
    public boolean getLogPendingTasks() {
        return LOG_PENDING_TASKS;
    }

    /** {@inheritDoc} */
    @Override
    public String getLogFile() {
        return logFile;
    }

    /** {@inheritDoc} */
    @Override
    public String getHighScoresFilePath() {
        return HIGHCORES_DATA;
    }

    /**
     * Interpret the JSON for the Constants class.
     * @param json The json map.
     */
    private void interpretJson(Map<String, String> json) {
        for (Map.Entry<String, String> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "logFile":
                    logFile = entry.getValue();
                    break;
                default:
                    System.err.print("The json entry \"" + entry.getKey());
                    System.err.print(entry.getKey());
                    System.err.println("\" in the configuration file could not be identified");
            }
        }
    }

}
