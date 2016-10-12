package progression;


import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Map;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class SaveFileHighScoreEntry {
    private Map<String, String> highscores;

    public SaveFileHighScoreEntry() {

    }

    public Map<String, String> getHighscores() {
        return highscores;
    }

    public void setHighscores(Map<String, String> highscores) {
        this.highscores = highscores;
    }
}