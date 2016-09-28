package constants;

import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.util.Map;

public class Constants implements IConstants {

    /**
     * The service locator for the Constants class.
     */
    private static transient IServiceLocator sL;

    public static void register(IServiceLocator sL) {
        assert sL != null;
        Constants.sL = sL;
        sL.provide(new Constants());
    }
    /**
     * The width of the frame of the game
     */
    private static final int width = 640;
    /**
     * The height of the frame of the game
     */
    private static final int height = 960;
    /**
     * How much the doodle is affected by gravity.
     */
    private static final double gravityAcceleration = 0.5d;
    /**
     * The height the dodle jumps will be multiplied with this value to obtain the score that the player will get.
     * each frame.
     */
    private static final double scoreMultiplier = 0.15;
    /**
     * True if the number of pending tasks of the logging thread executor should be logged each time something is logged.
     */
    private static final boolean LOG_PENDING_TASKS = true;
    /**
     * The file to which the logs will be written to.
     */
    private static String logFile;
    /**
     * The file to which the highscores will be written to.
     */
    private static String highScoresFile = "highscores.data";

    /**
     * Prevent public instantiation of Constants.
     */
    private Constants() {
        try {
            Map<String, String> json = (Map<String, String>) sL.getFileSystem().parseJsonMap("constants.json", String.class);
            interpretJson(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGameWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGameHeight() {
        return height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getGravityAcceleration() {
        return gravityAcceleration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getScoreMultiplier() {
        return scoreMultiplier;
    }

    /** {@inheritDoc} */
    @Override
    public boolean getLogPendingTasks() {
        return LOG_PENDING_TASKS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogFile() {
        return logFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHighScoresFile() {
        return highScoresFile;
    }

    /**
     * Interpret the JSON for the Constants class
     * @param json The json map.
     */
    private void interpretJson(Map<String, String> json) {
        for (Map.Entry<String, String> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "logFile":
                    logFile = entry.getValue();
                    break;
                default:
                    System.err.println("The json entry \"" + entry.getKey() + "\" in the configuration file could not be identified");
            }
        }
    }

}
