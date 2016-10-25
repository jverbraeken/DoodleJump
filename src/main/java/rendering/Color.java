package rendering;

/**
 * Contains type-safe colors used for the {@link IRenderer renderer}.
 */
public enum Color {
    /**
     * White.
     */
    white(255, 255, 255),
    /**
     * Black.
     */
    black(0, 0, 0),
    /**
     * The color of the even score entries.
     */
    scoreEntryEven(235, 224, 206),
    /**
     * The color of the uneven score entries.
     */
    scoreEntryUneven(241, 234, 224);

    /**
     * The Java AWT Color corresponding to this color.
     */
    private final java.awt.Color color;

    /**
     * Creates a new Java AWT Color.
     * @param red The red component (between 0 and 255 inclusive)
     * @param green The green component (between 0 and 255 inclusive)
     * @param blue The blue component (between 0 and 255 inclusive)
     */
    Color(final int red, final int green, final int blue) {
        color = new java.awt.Color(red, green, blue);
    }

    /**
     * @return The Java AWT color corresponding to this enum value
     */
    java.awt.Color getColor() {
        return color;
    }
}
