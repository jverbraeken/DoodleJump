package objects.blocks;

import java.util.HashMap;
import java.util.Map;

/**
 * Static class that maps key names to keyCodes.
 */
public final class WeightsMap {

    /**
     * Map that maps enums of the key to the keyCode.
     */
    private static final Map<WeightsEnum, Double> WEIGTS_MAP = new HashMap<>();
    /**
     * The chance of the normal platform spawning.
     */
    private static final double NORMAL_PLATFORM = 0.8;

    /**
     * The chance of the horizontal moving platform spawning.
     */
    private static final double HORIZONTAL_MOVING_PLATFORM = 0.05;

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
        WEIGTS_MAP.put(WeightsEnum.normalPlatform, NORMAL_PLATFORM);
        WEIGTS_MAP.put(WeightsEnum.horizontalMovingPlatform, HORIZONTAL_MOVING_PLATFORM);
        WEIGTS_MAP.put(WeightsEnum.verticalMovingPlatform, VERTICAL_MOVING_PLATFORM);
        WEIGTS_MAP.put(WeightsEnum.breakingPlatform, BREAKING_PLATFORM);

        // Powerups
        WEIGTS_MAP.put(WeightsEnum.spring, SPRING_POWERUP);
        WEIGTS_MAP.put(WeightsEnum.trampoline, TRAMPOLINE_POWERUP);
        WEIGTS_MAP.put(WeightsEnum.jetpack,JETPACK_POWERUP);
        WEIGTS_MAP.put(WeightsEnum.propellor, PROPELLOR_POWERUP);
        WEIGTS_MAP.put(WeightsEnum.sizeUp, SIZEUP_POWERUP);
        WEIGTS_MAP.put(WeightsEnum.sizeDown, SIZEDOWN_POWERUP);
        WEIGTS_MAP.put(WeightsEnum.springShoes, SPRINGSHOES_POWERUP);

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
     * @return The {@link WeightsEnum} enum corresponding with the integer value
     */
    /* package */ static Double getWeight(final WeightsEnum elementType) {
        return WEIGTS_MAP.get(elementType);
    }

}
