package constants;

public interface IConstants {
    /**
     * @return The width of the frame of the game
     */
    int getGameWidth();

    /**
     * @return The height of the frame of the game
     */
    int getGameHeight();

    /**
     * @return The acceleration of the gravity for the doodle
     */
    double getGravityAcceleration();

    /**
     * @return The amount of which the y position of the doodle should get multiplied to obtain the score
     */
    double getScoreMultiplier();

    /**
     * @return True if the number of pending tasks of the logging thread executor should be logged
     */
    boolean getLogPendingTasks();

    /**
     * @return The name of the log file
     * @return
     */
    String getLogFile();
}
