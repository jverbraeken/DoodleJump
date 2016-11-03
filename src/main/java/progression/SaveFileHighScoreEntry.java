package progression;

/**
 * Used to be (de)serialized to save/load the data.
 * <br />
 * <br />
 * <b>NOTE: this class is designed to be used by JSON (de)serializers only and is not meant
 * for regular usage in the game!</b>
 */
/* package */ final class SaveFileHighScoreEntry {

    /**
     * The name associated with the high score.
     */
    private String name;
    /**
     * The score associated with the high score.
     */
    private int score;

    /**
     * Construct a new HighScore entry.
     */
    /* package */ SaveFileHighScoreEntry() {
    }

    /**
     * @return The name associated with the high score
     */
    public String getName() {
        return this.name;
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
        return this.score;
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
        return "HighScoreEntry{" + "name='" + this.name + '\'' + ", score=" + this.score + '}';
    }

}
