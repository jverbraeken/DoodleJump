package progression;

import objects.powerups.Powerups;

import java.util.List;

/**
 * Defines a progression manager.
 */
public interface IProgressionManager {

    /**
     * Initializes the progression manager. Normally the progression will be loaded from the disk in this method.
     */
    void init();

    /**
     * Adds a new high score and saves it to the disk.
     *
     * @param name  The name associated with the new highscore
     * @param score The score associated with the new highscore
     */
    void addHighScore(final String name, final double score);

    /**
     * @return A list containing the highscores of the player.
     */
    List<HighScore> getHighscores();

    /**
     * @return The amount of coins the player has.
     */
    int getCoins();

    /**
     * @return A list containing the active missions.
     */
    List<Mission> getMissions();

    /**
     * Let the progression manager know that the mission is finished.
     *
     * @param mission The mission that's finished
     */
    void alertMissionFinished(final Mission mission);

    /**
     * @param powerup The powerup you want to retrieve the current level from. Cannot be null
     * @return The level of the powerup
     */
    int getPowerupLevel(final Powerups powerup);

    /**
     * Decreases the amount of coins with {@code price}.
     *
     * @param amount The amount of coins that should be subtracted from the total amount of coins
     */
    void decreaseCoins(final int amount);

    /**
     * Increases the powerup level of the powerup specified by 1.
     *
     * @param powerup The powerup that should be upgraded
     */
    void increasePowerupLevel(final Powerups powerup);

    void update();
}
