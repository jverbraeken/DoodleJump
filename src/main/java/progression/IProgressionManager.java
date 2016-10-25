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
     * Returns the ranks of this player.
     * @return The ranks of this player.
     */
    Ranks getRank();

    /**
     * @return The amount of experience the player has.
     */
    int getExperience();

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
     * Decreases the amount of coins with {@code amount}.
     *
     * @param amount The amount of coins that should be subtracted from the total amount of coins
     */
    void decreaseCoins(final int amount);

    /**
     * Increases the amount of experience with {@code amount}.
     *
     * @param amount The amount of experience that should be added to the total amount of coins
     */
    void addExperience(final int amount);

    /**
     * Increases the powerup level of the powerup specified by 1.
     *
     * @param powerup The powerup that should be upgraded
     */
    void increasePowerupLevel(final Powerups powerup);

    void update();
}
