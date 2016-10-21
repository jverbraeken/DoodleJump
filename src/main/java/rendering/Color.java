package rendering;

public enum Color {
    /**
     * White
     */
    white(255, 255, 255),
    /**
     * Black
     */
    black(0, 0, 0),
    /**
     * The color of the even score entries
     */
    scoreEntryEven(235, 224, 206),
    /**
     * The color of the uneven score entries
     */
    scoreEntryUneven(241, 234, 224);

    private final java.awt.Color color;

    /**
     * Creates a new Java AWT Color
     * @param red The red component (between 0 and 255 inclusive)
     * @param green The green component (between 0 and 255 inclusive)
     * @param blue The blue component (between 0 and 255 inclusive)
     */
    Color(int red, int green, int blue) {
        color = new java.awt.Color(red, green, blue);
    }

    java.awt.Color getColor() {
        return color;
    }
}
