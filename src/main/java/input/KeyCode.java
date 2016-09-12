package input;

import java.util.EnumMap;
import java.util.Map;

/**
 * Static class that maps key names to keyCodes.
 */
public class KeyCode {

    /**
     * Map that maps enums of the key to the keyCode.
     */
    private static final Map<Keys, Integer> keyMap = new EnumMap<>(Keys.class);

    /**
     * Prevent KeyCodes instances.
     */
    private KeyCode() { }

    /**
     * Initialize the keyMap.
     */
    /* package */ static void init() {
        // Arrows
        keyMap.put(Keys.arrowLeft, 37);
        keyMap.put(Keys.arrowRight, 39);

        // Letters
        keyMap.put(Keys.a, 65);
        keyMap.put(Keys.d, 68);
    }

    /**
     * Get the keycode given the key.
     *
     * @param key Keys.Key, e.g. Keys.arrowLeft.
     * @return The keyCode.
     */
    public static int getKeyCode(Keys key) {
        return keyMap.get(key);
    }

}
