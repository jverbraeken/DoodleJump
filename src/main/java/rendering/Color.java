package rendering;

/**
 * Contains type-safe colors used for the {@link IRenderer renderer}.
 */
public enum Color {
    /**
     * White.
     */
    white(255, 255, 255, 255),

    /**
     * Half opaque white.
     */
    halfOpaqueWhite(255, 255, 255, 150),
    /**
     * A nice darkblue.
     */
    darkBlue(9, 36, 76, 255),
    /**
     * Black.
     */
    black(0, 0, 0, 255),
    /**
     * The color of the even score entries.
     */
    scoreEntryEven(235, 224, 206, 255),
    /**
     * The color of the uneven score entries.
     */
    scoreEntryUneven(241, 234, 224, 255);

    /**
     * The java.awt.Color class that belongs to the enum.
     */
    private final java.awt.Color color;

    /**
     * Creates a new Java AWT Color.
     *
     * @param red     The red component (between 0 and 255 inclusive)
     * @param green   The green component (between 0 and 255 inclusive)
     * @param blue    The blue component (between 0 and 255 inclusive)
     * @param opacity The opacity component (between 0 and 255 inclusive)
     */
    Color(final int red, final int green, final int blue, final int opacity) {
        color = new java.awt.Color(red, green, blue, opacity);
    }

    /**
     * Returns the variable color.
     *
     * @return the color as java.awt.Color.
     */
    java.awt.Color getColor() {
        return color;
    }
}
