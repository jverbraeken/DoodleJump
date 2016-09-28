package system;

/**
 * Class representing a score.
 */
public class Score implements Comparable<Score> {

    /**
     * The name of this Score.
     */
    private String name;
    /**
     * The score fo this Score.
     */
    private double score;

    /**
     * Package protected constructor so only Game can create a score.
     * @param n The name for the score.
     * @param s The actual score.
     */
    /* package */ Score(final String n, final Double s) {
        this.name = n;
        this.score = s;
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(Score that) {
        if(this.getScore() > that.getScore()) {
            return -1;
        } else if(this.getScore() < that.getScore()) {
            return 1;
        } else {
            return 0;
        }
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
    public Double getScore() {
        return this.score;
    }

}
