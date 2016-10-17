package progression;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import filesystem.IToJsonSerializable;

/**
 * Used to be (de)serialized to save/load the data.
 * <br />
 * <br />
 * <b>NOTE: this class is designed to be used by JSON (de)serializers only and is not meant
 * for regular usage in the game!</b>
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public final class SaveFileHighScoreEntry implements IToJsonSerializable {
    /**
     * The name associated with the high score.
     */
    private String name;
    /**
     * The score associated with the high score.
     */
    private int score;

    /**
     * Construct a new Highscore entry.
     */
    public SaveFileHighScoreEntry() {
    }

    /**
     * @return The name associated with the high score
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name the high score entry should have
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return The score associated with the high score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score The score the high score entry should have
     */
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * @return A string describing the high score entry
     */
    @Override
    public String toString() {
        return "HighScoreEntry{" + "name='" + name + '\'' + ", score=" + score + '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJson() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"name\":");
        sb.append("\"" + name + "\"");
        sb.append(",");
        sb.append("\"score\":");
        sb.append(score);
        sb.append("}");
        return sb.toString();
    }
}
