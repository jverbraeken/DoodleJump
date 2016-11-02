package progression;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.annotation.Nonnull;

/**
 * Class representing a score. <b>Is immutable</b>.
 */
public final class HighScore implements Comparable<HighScore> {

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
     * @param name The name for the score.
     * @param score The actual score.
     */
    /* package */ HighScore(final String name, final double score) {
        this.name = name;
        this.score = (int) score;
    }

    /**
     * Package protected constructor so only Game can create a score.
     *
     * @param name The name for the score.
     * @param score The actual score.
     */
    /* package */ HighScore(final String name, final String score) {
        this.name = name;
        this.score = Integer.parseInt(score);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(@Nonnull final HighScore that) {
        if (this.getScore() > that.getScore()) {
            return -1;
        } else if (this.getScore() < that.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the score.
     * @return the score.
     */
    public int getScore() {
        return this.score;
    }

}
