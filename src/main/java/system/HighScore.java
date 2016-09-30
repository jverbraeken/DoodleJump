package system;

/**
 * Class representing a score.
 */
/* package */ class HighScore implements Comparable<HighScore> {

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
     * Package protected constructor so only Game can create a score.
     *
     * @param n The name for the score.
     * @param s The actual score.
     */
    /* package */ HighScore(final String n, final String s) {
        this.name = n;
        this.score = Integer.parseInt(s);
    }

    /** {@inheritDoc} */
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
