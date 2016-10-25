package input;

import java.util.HashMap;
import java.util.Map;

/**
 * Static class that maps key names to keyCodes.
 */
public final class KeyCode {

    /**
     * Map that maps enums of the key to the keyCode.
     */
    private static final Map<Integer, Keys> KEY_MAP = new HashMap<>();
    /**
     * Provides a very fast lookup of a key in {@link #KEY_MAP} by its value.
     */
    private static final Map<Keys, Integer> KEYCODE_MAP = new HashMap<>();
    /**
     * The identifier of the Left arrow key.
     */
    private static final int ARROW_LEFT_KEY = 37;
    /**
     * The identifier of the Right arrow key.
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
        KEY_MAP.put(ARROW_LEFT_KEY, Keys.arrowLeft);
        KEYCODE_MAP.put(Keys.arrowLeft, ARROW_LEFT_KEY);
        KEY_MAP.put(ARROW_RIGHT_KEY, Keys.arrowRight);
        KEYCODE_MAP.put(Keys.arrowRight, ARROW_RIGHT_KEY);

        // Letters
        KEY_MAP.put(A_KEY, Keys.a);
        KEYCODE_MAP.put(Keys.a, A_KEY);
        KEY_MAP.put(D_KEY, Keys.d);
        KEYCODE_MAP.put(Keys.d, D_KEY);

        // Miscellaneous
        KEY_MAP.put(ENTER_KEY, Keys.enter);
        KEYCODE_MAP.put(Keys.enter, ENTER_KEY);
        KEY_MAP.put(SPACE_KEY, Keys.space);
        KEYCODE_MAP.put(Keys.space, SPACE_KEY);
    }

    /**
     * Prevent KeyCodes instances.
     */
    private KeyCode() {
    }

    /**
     * Get the key given the key code.
     *
     * @param keyCode The integer code of the key
     * @return The {@link Keys} enum corresponding with the integer value
     */
    static Keys getKey(final int keyCode) {
        return KeyCode.KEY_MAP.get(keyCode);
    }

    /**
     * Get the key code given the key.
     *
     * @param keyCode The integer code of the key
     * @return The {@link Keys} enum corresponding with the integer value
     */
    static int getKeyCode(final Keys keyCode) {
        return KeyCode.KEYCODE_MAP.get(keyCode);
    }

}
