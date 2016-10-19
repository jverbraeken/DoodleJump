package objects.powerups;

/**
 * Indicates which types of powerups are available
 */
public enum Powerups {
    JETPACK("jetpack", 50),
    PROPELLER("propeller", 50),
    SIZEDOWN("sizeDown", 50),
    SIZEUP("sizeUp", 50),
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

    Powerups(final String name, final int... price) {
        this.name = name;
        this.price = price;
    }

    int getMaxLevel() {
        return price.length;
    }

    int getPrice(final int level) {
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
