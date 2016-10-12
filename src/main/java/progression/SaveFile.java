package progression;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;
import java.util.Map;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class SaveFile {
    private List<SaveFileHighScoreEntry> highScores;
    private int coins;
    private Map<String, Integer> powerupLevels;

    public SaveFile() {

    }

    public List<SaveFileHighScoreEntry> getHighScores() {
        return highScores;
    }

    public void setHighScores(List<SaveFileHighScoreEntry> highScores) {
        this.highScores = highScores;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Map<String, Integer> getPowerupLevels() {
        return powerupLevels;
    }

    public void setPowerupLevels(Map<String, Integer> powerupLevels) {
        this.powerupLevels = powerupLevels;
    }
}