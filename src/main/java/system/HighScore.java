package system;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class representing a score.
 */
public class HighScore implements Comparable<HighScore> {

    /**
     * The name of this HighScore.
     */
    private final String name;
    /**
     * The score fo this HighScore.
     */
    private final int score;

    /**
     * Package protected constructor so only Game can create a score.
     *
     * @param n The name for the score.
     * @param s The actual score.
     */
    /* package */ HighScore(final String n, final double s) {
        this.name = n;
        this.score = (int) s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final HighScore that) {
        if (this.getScore() > that.getScore()) {
            return -1;
        } else if (this.getScore() < that.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HighScore highScore = (HighScore) o;

        return new EqualsBuilder()
                .append(score, highScore.score)
                .append(name, highScore.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        final int hash1 = 17;
        final int hash2 = 37;
        return new HashCodeBuilder(hash1, hash2)
                .append(name)
                .append(score)
                .toHashCode();
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the score.
     *
     * @return the score.
     */
    public int getScore() {
        return this.score;
    }

}
