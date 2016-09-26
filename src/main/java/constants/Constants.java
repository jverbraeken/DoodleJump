package constants;

import com.bluelinelabs.logansquare.annotation.JsonObject;
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

    private final ILogger logger;
    /**
     * The width of the frame of the game
     */
    private int width;
    /**
     * The height of the frame of the game
     */
    private int height;
    /**
     * How much the doodle is affected by gravity.
     */
    private double gravityAcceleration;
    /**
     * The height the dodle jumps will be multiplied with this value to obtain the score that the player will get
     * each frame.
     */
    private double scoreMultiplier;

    /**
     * Prevent public instantiation of Constants.
     */
    private Constants() {
        logger = sL.getLoggerFactory().createLogger(this.getClass());
        try {
            Map<String, String> json = (Map<String, String>) sL.getFileSystem().parseJsonMap("constants.json", String.class);
            interpretJson(json);
        } catch (FileNotFoundException e) {
            logger.error(e);
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

    private void interpretJson(Map<String, String> json) {
        for (Map.Entry<String, String> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "width":
                    try {
                        this.width = Integer.valueOf(entry.getValue());
                    } catch (NumberFormatException e) {
                        logger.error("The width value specified in the configuration file is not a valid integer value.");
                        e.printStackTrace();
                    }
                    break;
                case "height":
                    try {
                        this.height = Integer.valueOf(entry.getValue());
                    } catch (NumberFormatException e) {
                        logger.error("The height value specified in the configuration file is not a valid integer value.");
                        e.printStackTrace();
                    }
                    break;
                case "gravityAcceleration":
                    try {
                        this.gravityAcceleration = Double.valueOf(entry.getValue());
                    } catch (NumberFormatException e) {
                        logger.error("The gravityAcceleration value specified in the configuration file is not a valid integer value.");
                        e.printStackTrace();
                    }
                    break;
                case "scoreMultiplier":
                    try {
                        this.scoreMultiplier = Double.valueOf(entry.getValue());
                    } catch (NumberFormatException e) {
                        logger.error("The scoreMultiplier value specified in the configuration file is not a valid integer value.");
                        e.printStackTrace();
                    }
                    break;
                default:
                    logger.warning("The json entry \"" + entry.getKey() + "\" in the configuration file could not be identified");
            }
        }
    }

    /*@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
    private class ConstantsJson {

        private int width;
        private int height;
        private int gravityAcceleration;
        private int scoreMultiplier;


        private ConstantsJson() {

        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getGravityAcceleration() {
            return gravityAcceleration;
        }

        public void setGravityAcceleration(int gravityAcceleration) {
            this.gravityAcceleration = gravityAcceleration;
        }

        public int getScoreMultiplier() {
            return scoreMultiplier;
        }

        public void setScoreMultiplier(int scoreMultiplier) {
            this.scoreMultiplier = scoreMultiplier;
        }

        @Override
        public String toString() {
            return "ConstantsJson{" +
                    "width=" + width +
                    ", height=" + height +
                    ", gravityAcceleration=" + gravityAcceleration +
                    ", scoreMultiplier=" + scoreMultiplier +
                    '}';
        }
    }*/
}
