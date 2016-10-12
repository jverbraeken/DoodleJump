package progression;


import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class HighScoreEntry {
    private String name;
    private int score;

    public HighScoreEntry() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "HighScoreEntry{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}