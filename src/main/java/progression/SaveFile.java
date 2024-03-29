package progression;

import java.util.List;
import java.util.Map;

/**
 * Used to be (de)serialized to load/save the progression of the player.
 * <br />
 * <br />
 * <b>NOTE: this class is designed to be used by JSON (de)serializers only and is not meant
 * for regular usage in the game!</b>
 */
/* package */ final class SaveFile {

    /**
     * Lists the high scores of the player.
     */
    private List<SaveFileHighScoreEntry> highScores;
    /**
     * The amount of coins.
     */
    private int coins;
    /**
     * The amount of experience.
     */
    private int experience;
    /**
     * Contains the levels of each powerup: Map< name, level> . The name must be the same as the enum.
     */
    private Map<String, Integer> powerupLevels;

    /**
     * Construct a new save file.
     */
    /* package */ SaveFile() {
    }

    /**
     * @return A list containing the high score entries ready to be serialized
     */
    /* package */ List<SaveFileHighScoreEntry> getHighScores() {
        return this.highScores;
    }

    /**
     * @param highScores The list containing the highscores that should be saved
     */
    /* package */ void setHighScores(final List<SaveFileHighScoreEntry> highScores) {
        this.highScores = highScores;
    }

    /**
     * @return The amount of coins
     */
    public int getCoins() {
        return this.coins;
    }

    /**
     * @param coins The amount of coins that should be saved
     */
    public void setCoins(final int coins) {
        this.coins = coins;
    }

    /**
     * Will return the amount of experience from the SaveFile.
     *
     * @return The amount of experience.
     */
    public int getExperience() {
        return this.experience;
    }

    /**
     * Sets the experience variable to the parameter given.
     *
     * @param experience The amount of experience that should be saved.
     */
    public void setExperience(final int experience) {
        this.experience = experience;
    }

    /**
     * @return A Map associating the name of the APowerup enum with its level
     */
    /* package */ Map<String, Integer> getPowerupLevels() {
        return this.powerupLevels;
    }

    /**
     * @param powerupLevels A Map containing the levels of the powerups in the game
     */
    /* package */ void setPowerupLevels(final Map<String, Integer> powerupLevels) {
        this.powerupLevels = powerupLevels;
    }

}
