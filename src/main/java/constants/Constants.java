package constants;

import logging.ILogger;
import system.IServiceLocator;

import java.io.FileNotFoundException;
import java.util.Map;

public class Constants implements IConstants {

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
    private static final double gravityAcceleration = 0.5;
    /**
     * The height the dodle jumps will be multiplied with this value to obtain the score that the player will get.
     * each frame.
     */
    private static final double scoreMultiplier = 0.15;
    /**
     * The file to which the logs will be written to.
     */
    private String logFile;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogFile() {
        return logFile;
    }

    private void interpretJson(Map<String, String> json) {
        for (Map.Entry<String, String> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "logFile":
                    this.logFile = entry.getValue();
                    break;
                default:
                    System.err.println("The json entry \"" + entry.getKey() + "\" in the configuration file could not be identified");
            }
        }
    }
}
