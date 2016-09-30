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
        KEY_MAP.put(ARROW_RIGHT_KEY, Keys.arrowRight);

        // Letters
        KEY_MAP.put(A_KEY, Keys.a);
        KEY_MAP.put(D_KEY, Keys.d);

        // Miscellaneous
        KEY_MAP.put(ENTER_KEY, Keys.enter);
        KEY_MAP.put(SPACE_KEY, Keys.space);
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
    /* package */ static Keys getKey(final int keyCode) {
        return KEY_MAP.get(keyCode);
    }

}
