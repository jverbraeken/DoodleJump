package progression;

/**
 * An enum with the Ranks and their corresponding amount of experience required.
 */
public enum Ranks {

    /**
     * The first rank.
     */
    rookieJumper(0),

    /**
     * The second rank.
     */
    firstJumper(10000),

    /**
     * The third rank.
     */
    partyPooper(10000),

    /**
     * The fourth rank.
     */
    tiredLegs(10000),

    /**
     * The fifth rank.
     */
    oldMan(10000),

    /**
     * The sixth rank.
     */
    tooEasy(10000),

    /**
     * The last rank.
     */
    theBoss(10000);

    /**
     * The experience needed to reach this rank.
     */
    private int experience;

    /**
     * Create a Ranks enum object.
     * @param exp the amount of experience needed.
     */
    Ranks (final int exp) {
        experience = exp;
    }

    /**
     * Returns the amount of experience needed for the Rank.
     * @return the variable experience.
     */
    public int getExperience() {
        return experience;
    }


}
