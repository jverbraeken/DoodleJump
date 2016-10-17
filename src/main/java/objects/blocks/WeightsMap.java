package objects.blocks;

import java.util.EnumMap;

/**
 * Static class that maps key names to keyCodes.
 */
/* package */ final class WeightsMap {

    /**
     * Map that maps enums of the key to the keyCode.
     */
    private static final EnumMap<ElementTypes, Double> WEIGTS_MAP = new EnumMap<>(ElementTypes.class);
    /**
     * The chance of the normal platform spawning.
     */
    private static final double NORMAL_PLATFORM = 0.8;

    /**
     * The chance of the horizontal moving platform spawning.
     */
    private static final double HORIZONTAL_MOVING_PLATFORM = 0.5;

    /**
     * The chance of the vertical moving platform spawning.
     */
    private static final double VERTICAL_MOVING_PLATFORM = 0.05;

    /**
     * The chance of the breaking platform spawning.
     */
    private static final double BREAKING_PLATFORM = 0.1;


    /**
     * The chance of the spring powerup spawning.
     */
    private static final double SPRING_POWERUP = 0.03;

    /**
     * The chance of the spring trampoline spawning.
     */
    private static final double TRAMPOLINE_POWERUP = 0.008;

    /**
     * The chance of the spring jetpack spawning.
     */
    private static final double JETPACK_POWERUP = 0.003;

    /**
     * The chance of the spring jetpack spawning.
     */
    private static final double PROPELLOR_POWERUP = 0.005;

    /**
     * The chance of the spring jetpack spawning.
     */
    private static final double SIZEUP_POWERUP = 0.005;

    /**
     * The chance of the spring jetpack spawning.
     */
    private static final double SIZEDOWN_POWERUP = 0.005;

    /**
     * The chance of the spring jetpack spawning.
     */
    private static final double SPRINGSHOES_POWERUP = 0.004;

    static {
        // Platforms
        WEIGTS_MAP.put(ElementTypes.normalPlatform, NORMAL_PLATFORM);
        WEIGTS_MAP.put(ElementTypes.horizontalMovingPlatform, HORIZONTAL_MOVING_PLATFORM);
        WEIGTS_MAP.put(ElementTypes.verticalMovingPlatform, VERTICAL_MOVING_PLATFORM);
        WEIGTS_MAP.put(ElementTypes.breakingPlatform, BREAKING_PLATFORM);

        // Powerups
        WEIGTS_MAP.put(ElementTypes.spring, SPRING_POWERUP);
        WEIGTS_MAP.put(ElementTypes.trampoline, TRAMPOLINE_POWERUP);
        WEIGTS_MAP.put(ElementTypes.jetpack, JETPACK_POWERUP);
        WEIGTS_MAP.put(ElementTypes.propellor, PROPELLOR_POWERUP);
        WEIGTS_MAP.put(ElementTypes.sizeUp, SIZEUP_POWERUP);
        WEIGTS_MAP.put(ElementTypes.sizeDown, SIZEDOWN_POWERUP);
        WEIGTS_MAP.put(ElementTypes.springShoes, SPRINGSHOES_POWERUP);

    }

    /**
     * Prevent WeightsMap instances.
     */
    private WeightsMap() {
    }

    /**
     * Get the weight given the elementType.
     *
     * @param elementType The integer code of the key
     * @return The {@link ElementTypes} enum corresponding with the integer value
     */
    /* package */ static Double getWeight(final ElementTypes elementType) {
        return WEIGTS_MAP.get(elementType);
    }

}
