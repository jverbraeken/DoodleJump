package progression;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * Used to be (de)serialized to load/save the progression of the player.
 * <br />
 * <br />
 * <b>NOTE: this class is designed to be used by JSON (de)serializers only and is not meant
 * for regular usage in the game!</b>
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public final class SaveFile {
    /**
     * Lists the highscores of the player.
     */
    private List<SaveFileHighScoreEntry> highScores;
    /**
     * The amount of coins.
     */
    private int coins;
    /**
     * Contains the levels of each powerup: Map< name, level> . The name must be the same as the enum
     */
    private Map<String, Integer> powerupLevels;

    /**
     * Construct a new save file.
     */
    public SaveFile() {

    }

    /**
     * @return A list containing the high score entries ready to be serialized
     */
    public List<SaveFileHighScoreEntry> getHighScores() {
        return highScores;
    }

    /**
     * @param highScores The list containing the highscores that should be saved
     */
    public void setHighScores(final List<SaveFileHighScoreEntry> highScores) {
        this.highScores = highScores;
    }

    /**
     * @return The amount of coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * @param coins The amount of coins that should be saved
     */
    public void setCoins(final int coins) {
        this.coins = coins;
    }

    /**
     * @return A Map associating the name of the Powerup enum with its level
     */
    public Map<String, Integer> getPowerupLevels() {
        return powerupLevels;
    }

    /**
     * @param powerupLevels A Map containing the levels of the powerups in the game
     */
    public void setPowerupLevels(final Map<String, Integer> powerupLevels) {
        this.powerupLevels = powerupLevels;
    }
}
