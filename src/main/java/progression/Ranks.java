package progression;

/**
 * An enum with the Ranks and their corresponding amount of experience required.
 */
public enum Ranks {

    /**
     * The first rank.
     */
    newbie(0, "Rookie jumper", 0),

    /**
     * The second rank.
     */
    starter(10000, "Starting jumper", 1),

    /**
     * The third rank.
     */
    partyPooper(100000, "Party pooper", 2),

    /**
     * The fourth rank.
     */
    tiredLegs(250000, "Tired-legs", 3),

    /**
     * The fifth rank.
     */
    oldMan(750000, "Old man", 4),

    /**
     * The sixth rank.
     */
    tooEasy(1500000, "Too easy", 5),

    /**
     * The last rank.
     */
    theBoss(3000000, "Tha Boss", 6);

    /**
     * The experience needed to reach this rank.
     */
    private int experience;

    /**
     * The name of this rank.
     */
    private String name;

    /**
     * The level of this rank.
     */
    private int level;

    /**
     * Create a Ranks enum object.
     * @param exp the amount of experience needed.
     * @param n the name of the rank.
     * @param l the level this rank represents.
     */
    Ranks(final int exp, final String n, final int l) {
        experience = exp;
        name = n;
        level = l;
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

    /**
     * Returns level as a number.
     * @return the variable level.
     */
    public int getLevelNumber() {
        return level;
    }


}
