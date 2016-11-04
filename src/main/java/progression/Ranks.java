package progression;

/**
 * An enum with the Ranks and their corresponding amount of experience required.
 */
public enum Ranks {

    /**
     * The first rank.
     */
    newbie(0, "Newbie", 0),

    /**
     * The second rank.
     */
    starter(5000, "Starter", 1),

    /**
     * The third rank.
     */
    partyPooper(10000, "Party pooper", 2),

    /**
     * The fourth rank.
     */
    tiredLegs(20000, "Tired-legs", 3),

    /**
     * The fifth rank.
     */
    oldMan(40000, "Old man", 4),

    /**
     * The sixth rank.
     */
    tooEasy(80000, "Too easy", 5),

    /**
     * The seventh rank.
     */
    theBoss(160000, "Tha Boss", 6),

    /**
     * The eight rank.
     */
    doodleMaster(320000, "Doodle Master", 7),

    /**
     * The ninth rank.
     */
    doodleGod(640000, "Doodle God", 8),

    /**
     * The tenth rank.
     */
    cheater(1280000, "You cheater!", 9);


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
    /* package */ Ranks(final int exp, final String n, final int l) {
        this.experience = exp;
        this.name = n;
        this.level = l;
    }

    /**
     * Returns the amount of experience needed for the Rank.
     * @return the variable experience.
     */
    public int getExperience() {
        return this.experience;
    }

    /**
     * Returns the name of this Rank.
     * @return the variable name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns level as a number.
     * @return the variable level.
     */
    public int getLevelNumber() {
        return this.level;
    }

    /**
     * Returns the first Ranks corresponding to the levelNumber. If none is found, returns null.
     * @return the first Ranks corresponding to the levelNumber.
     */
    public static Ranks getRankByLevelNumber(final int levelNumber) {
        for (Ranks rank : Ranks.values()) {
            if (rank.getLevelNumber() == levelNumber) {
                return rank;
            }
        }
        return null;
    }

}
