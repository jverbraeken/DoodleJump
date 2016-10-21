package constants;

/**
 * Interface for the constants class.
 */
public interface IConstants {

    /**
     * Get the width of the game window.
     *
     * @return The width of the frame of the game.
     */
    int getGameWidth();

    /**
     * Get the height of the game window.
     *
     * @return The height of the frame of the game.
     */
    int getGameHeight();

    /**
     * Get the gravity acceleration.
     *
     * @return The acceleration of the gravity for the doodle.
     */
    double getGravityAcceleration();

    /**
     * Get the score multiplier.
     *
     * @return The amount of which the y position of the doodle should get multiplied to obtain the score.
     */
    double getScoreMultiplier();

    /**
     * Get the pending tasks for the logger.
     *
     * @return True if the number of pending tasks of the logging thread executor should be logged.
     */
    boolean getLogPendingTasks();

    /**
     * Get the log file.
     *
     * @return The name of the log file.
     */
    String getLogFile();

    /**
     * Get the file where all progression of the player is saved.
     *
     * @return The name of the save file.
     */
    String getSaveFilePath();

}
