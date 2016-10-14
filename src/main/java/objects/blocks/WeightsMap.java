package objects.blocks;

import java.util.EnumMap;

/**
 * Static class that maps key names to keyCodes.
 */
public final class WeightsMap {

    /**
     * Map that maps enums of the key to the keyCode.
     */
    private static final EnumMap<PlatformTypes, Double> WEIGTS_MAP = new EnumMap<>(PlatformTypes.class);
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

    /**
     * The chance of the spring jetpack spawning.
     */
    private static final double CANNON_POWERUP = 0.001;

    /**
     * The chance of the spring jetpack spawning.
     */
    private static final double ROCKETLAUNCHER_POWERUP = 0.001;

    static {
        // Platforms
        WEIGTS_MAP.put(PlatformTypes.normalPlatform, NORMAL_PLATFORM);
        WEIGTS_MAP.put(PlatformTypes.horizontalMovingPlatform, HORIZONTAL_MOVING_PLATFORM);
        WEIGTS_MAP.put(PlatformTypes.verticalMovingPlatform, VERTICAL_MOVING_PLATFORM);
        WEIGTS_MAP.put(PlatformTypes.breakingPlatform, BREAKING_PLATFORM);

        // Powerups
        WEIGTS_MAP.put(PlatformTypes.spring, SPRING_POWERUP);
        WEIGTS_MAP.put(PlatformTypes.trampoline, TRAMPOLINE_POWERUP);
        WEIGTS_MAP.put(PlatformTypes.jetpack,JETPACK_POWERUP);
        WEIGTS_MAP.put(PlatformTypes.propellor, PROPELLOR_POWERUP);
        WEIGTS_MAP.put(PlatformTypes.sizeUp, SIZEUP_POWERUP);
        WEIGTS_MAP.put(PlatformTypes.sizeDown, SIZEDOWN_POWERUP);
        WEIGTS_MAP.put(PlatformTypes.springShoes, SPRINGSHOES_POWERUP);
        WEIGTS_MAP.put(PlatformTypes.cannon, CANNON_POWERUP);
        WEIGTS_MAP.put(PlatformTypes.rocketLauncher, ROCKETLAUNCHER_POWERUP);

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
     * @return The {@link PlatformTypes} enum corresponding with the integer value
     */
    /* package */ static Double getWeight(final PlatformTypes elementType) {
        return WEIGTS_MAP.get(elementType);
    }

}
