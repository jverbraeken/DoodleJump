package objects.powerups;

/**
 * Indicates which types of powerups are available
 */
public enum Powerups {
    JETPACK("jetpack", 50),
    PROPELLER("propeller", 50),
    SIZEDOWN("sizeDown", 50),
    SIZEUP("sizeUp", 50),
    SHIELD("shield", 50),
    SPRING("spring", 0, 50, 150),
    SPRINGSHOES("springShoes", 50),
    TRAMPOLINE("trampoline", 50);

    /**
     * The prices of the levels of the powerup.
     */
    private final int[] price;
    /**
     * Used for debugging purposes; the name of the enum value.
     */
    private final String name;

    /**
     * Construct a new powerup.
     * @param name The name of the powerup, only used for debugging purposes
     * @param price The prices of all levels of the powerup, must be in the same order as the levels
     */
    Powerups(final String name, final int... price) {
        this.name = name;
        this.price = price;
    }

    /**
     * @return The maximum level of the powerup
     */
    public int getMaxLevel() {
        return price.length;
    }

    /**
     * Returns the price to upgrade to a certain level
     * @param level The level you want the amount of coins it costs to upgrade to from
     * @return The price in coins of the upgrade to the level specified
     */
    public int getPrice(final int level) {
        if (level <= 0) {
            throw new IllegalArgumentException("Powerups do not have levels lower than 0");
        }
        if (level >= price.length) {
            throw new IllegalArgumentException("The maximum level of the powerup \"" +
                    this.name +
                    "\" is " +
                    this.price.length +
                    ", while price was asked for a level exceeding this maximum level: " +
                    level);
        }
        return this.price[level];
    }
}
