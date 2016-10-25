package progression;

/**
 * An enum with the Ranks and their corresponding amount of experience required.
 */
public enum Ranks {

    /**
     * The first rank.
     */
    rookieJumper(0, "Rookie jumper"),

    /**
     * The second rank.
     */
    startingJumper(10000, "Starting jumper"),

    /**
     * The third rank.
     */
    partyPooper(100000, "Party pooper"),

    /**
     * The fourth rank.
     */
    tiredLegs(250000, "Tired-legs"),

    /**
     * The fifth rank.
     */
    oldMan(750000, "Old man"),

    /**
     * The sixth rank.
     */
    tooEasy(1500000, "Too easy"),

    /**
     * The last rank.
     */
    theBoss(3000000, "Tha Boss");

    /**
     * The experience needed to reach this rank.
     */
    private int experience;

    /**
     * The name of this rank.
     */
    private String name;

    /**
     * Create a Ranks enum object.
     * @param exp the amount of experience needed.
     * @param n the name of the rank.
     */
    Ranks(final int exp, final String n) {
        experience = exp;
        name = n;
    }

    /**
     * Returns the amount of experience needed for the Rank.
     * @return the variable experience.
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Returns the name of this Rank.
     * @return the variable name.
     */
    public String getName() {
        return name;
    }


}
