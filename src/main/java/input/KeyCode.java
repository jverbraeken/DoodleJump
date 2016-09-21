package input;

import java.util.EnumMap;
import java.util.Map;

/**
 * Static class that maps key names to keyCodes.
 */
public final class KeyCode {

    /**
     * Map that maps enums of the key to the keyCode.
     */
    private static final Map<Keys, Integer> KEY_MAP = new EnumMap<>(Keys.class);
    /**
     * The identifier of the left arrow key.
     */
    private static final int ARROW_LEFT_KEY = 37;
    /**
     * The identifier of the right arrow key.
     */
    private static final int ARROW_RIGHT_KEY = 39;
    /**
     * The identifier of the A key.
     */
    private static final int A_KEY = 65;
    /**
     * The identifier of the D key.
     */
    private static final int D_KEY = 68;
    /**
     * The identifier of the Enter key.
     */
    private static final int ENTER_KEY = 10;
    /**
     * The identifier of the Space key.
     */
    private static final int SPACE_KEY = 32;

    static {
        // Arrows
        KEY_MAP.put(Keys.arrowLeft, ARROW_LEFT_KEY);
        KEY_MAP.put(Keys.arrowRight, ARROW_RIGHT_KEY);

        // Letters
        KEY_MAP.put(Keys.a, A_KEY);
        KEY_MAP.put(Keys.d, D_KEY);

        // Miscellaneous
        KEY_MAP.put(Keys.enter, ENTER_KEY);
        KEY_MAP.put(Keys.space, SPACE_KEY);
    }

    /**
     * Prevent KeyCodes instances.
     */
    private KeyCode() {
    }

    /**
     * Get the keycode given the key.
     *
     * @param key Keys.Key, e.g. Keys.arrowLeft.
     * @return The keyCode.
     */
    public static int getKeyCode(final Keys key) {
        return KEY_MAP.get(key);
    }

}
