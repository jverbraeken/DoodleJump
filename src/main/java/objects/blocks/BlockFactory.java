package objects.blocks;

import math.ICalc;
import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.blocks.platform.Platform;
import objects.powerups.IPowerupFactory;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is the factory in which separate blocks get created.
 * In here one can specify the type of block one wants to create.
 */
public final class BlockFactory implements IBlockFactory {

    /**
     * The maximum amount of platforms per block.
     */
    private static final int MAX_PLATFORMS = 10;
    /**
     * The minimum amount of platforms per block.
     */
    private static final int MIN_PLATFORMS = 5;
    /**
     * Offset to place the trampoline on the proper place of a platform.
     */
    private static final int ITEM_Y_OFFSET = 5;
    /**
     * Offset to place the trampoline on the proper place of a platform.
     */
    private static final int TRAMPOLINE_X_OFFSET = 20;
    /**
     * Threshold in order to spawn a trampoline.
     * random int(10.000 > 9900)
     */
    private static final int TRAMPOLINE_THRESHOLD = 9900;
    /**
     * Threshold in order to spawn a trampoline.
     * random int(9500 < x < 9900)
     */
    private static final int SPRING_THRESHOLD = 9500;
    /**
     * Total threshold number for item generation.
     * random int(10000)
     */
    private static final int MAX_POWERUP_THRESHOLD = 10000;
    /**
     * A multiplier to generate a proper height deviation.
     */
    private static final double HEIGHT_DEVIATION_MULTIPLIER = 1.5;
    /**
     * An offset to generate a minimum height deviation.
     */
    private static final double HEIGHT_DEVIATION_OFFSET = 0.8;
    /**
     * The chance that a horizontal moving platform will spawn.
     */
    private static final double HORIZONTAL_CHANCE = 0.05;

    /**
     * The chance that a vertical moving platform will spawn.
     */
    private static final double VERTICAL_CHANCE = 0.05;
    /**
     * The chance that a vertical moving platform will spawn.
     */
    private static final double BREAK_CHANCE = 0.1;

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Register the block factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert sL != null;
        BlockFactory.serviceLocator = sL;
        BlockFactory.serviceLocator.provide(new BlockFactory());
    }

    /** {@inheritDoc} */
    @Override
    public synchronized IBlock createStartBlock() {
        Set<IGameObject> elements = new HashSet<>();

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(6, (serviceLocator.getConstants().getGameWidth() + serviceLocator.getConstants().getGameHeight()) / 130);
        int heightDividedPlatforms = serviceLocator.getConstants().getGameHeight() / platformAmount;

        IPlatform topJumpable = placeInitialStartBlockPlatforms(elements);
        IPlatform nowHighest = placeStartBlockPlatforms(elements, topJumpable, platformAmount, heightDividedPlatforms);
        return new Block(serviceLocator, elements, nowHighest);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized IBlock createBlock(final IJumpable topJumpable) {
        Set<IGameObject> elements = new HashSet<>();

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(MIN_PLATFORMS, MAX_PLATFORMS);
        int heightDividedPlatforms = serviceLocator.getConstants().getGameHeight() / platformAmount;

        IJumpable newTopJumpable = placeBlockPlatforms(elements, topJumpable, platformAmount, heightDividedPlatforms);

        return new Block(serviceLocator, elements, newTopJumpable);
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements                  The {@link Set} in which the platforms should be placed.
     * @param topJumpable               The highest platform created before the block starts (normally the latest
     *                                  platform created).
     * @param platformAmount            The amount of platforms.
     * @param heightDividedPlatforms    The height division of platforms (?).
     * @return The last and highest platform created by this method
     */
    private IJumpable placeBlockPlatforms(final Set<IGameObject> elements, final IJumpable topJumpable, final int platformAmount, final int heightDividedPlatforms) {
        assert topJumpable != null;
        assert platformAmount > 0;
        assert heightDividedPlatforms > 0;
        IJumpable newTopJumpable = topJumpable;
        for (int i = 0; i < platformAmount; i++) {
            newTopJumpable = placeFollowingPlatform(elements, newTopJumpable, heightDividedPlatforms);
        }
        return newTopJumpable;
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements The {@link Set} in which the platforms should be placed
     * @return The last and highest platform created by this method
     */
    private IPlatform placeInitialStartBlockPlatforms(final Set<IGameObject> elements) {
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPlatform platform = platformFactory.createPlatform(serviceLocator.getConstants().getGameWidth() / 2, (int) (serviceLocator.getConstants().getGameHeight() / 1.2));
        elements.add(platform);
        return platform;
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements                  The {@link Set} in which the platforms should be placed.
     * @param topJumpable               The highest platform created before the block starts (normally the latest
     *                                  platform created).
     * @param platformAmount            The amount of platforms.
     * @param heightDividedPlatforms    The height division of platforms (?).
     * @return The last and highest platform created.
     */
    private IPlatform placeStartBlockPlatforms(final Set<IGameObject> elements, final IPlatform topJumpable, final int platformAmount, final int heightDividedPlatforms) {
        assert topJumpable != null;
        assert platformAmount > 0;
        assert heightDividedPlatforms > 0;

        IPlatform highest = placeFollowingPlatform(elements, topJumpable, heightDividedPlatforms);
        for (int i = 2; i < platformAmount; i++) {
            highest = placeFollowingPlatform(elements, highest, heightDividedPlatforms);
        }
        return highest;
    }

    /**
     * Places a single platform part in the block specified.
     *
     * @param elements              A set of elements.
     * @param topJumpable           The highest platform created before the block starts (normally the latest platform created)
     * @param heightDivPlatforms    The height between the platforms
     * @return The last and highest platform created by this method
     */
    private IPlatform placeFollowingPlatform(final Set<IGameObject> elements, final IJumpable topJumpable, final int heightDivPlatforms) {
        IPlatform platform;
        boolean breaks;
        do {
            do {
                platform = makeFollowingPlatform(topJumpable, heightDivPlatforms);
            } while (platformCollideCheck(platform, elements));
            elements.add(platform);
            Platform.PlatformProperties br = Platform.PlatformProperties.breaks;
            breaks = platform.getProps().containsKey(br);
        } while (breaks);

        chanceForPowerup(elements, platform);
        return platform;
    }

    /**
     * Make a platform following the last platform.
     *
     * @param topJumpable The last jumpable object.
     * @param heightDividedPlatforms The height division for platforms.
     * @return A new platform.
     */
    private IPlatform makeFollowingPlatform(final IJumpable topJumpable, final int heightDividedPlatforms) {
        //TODO 1.7 and -0.8 are magic numbers
        double heightDeviation = serviceLocator.getCalc().getRandomDouble(HEIGHT_DEVIATION_MULTIPLIER) - HEIGHT_DEVIATION_OFFSET;
        double widthDeviation = serviceLocator.getCalc().getRandomDouble(1d);

        int yLast = (int) topJumpable.getYPos();
        int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

        double jumpTime = topJumpable.getBoost() / serviceLocator.getConstants().getGravityAcceleration();
        double maxY = (int) (serviceLocator.getConstants().getGravityAcceleration() * Math.pow(jumpTime, 2) / 2);
        if (yLoc < yLast - maxY) {
            yLoc = yLast - (int) maxY;
        }

        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPlatform platform = platformFactory.createPlatform(0, yLoc);

        double randDouble = serviceLocator.getCalc().getRandomDouble(1);
        if (randDouble < HORIZONTAL_CHANCE) {
            platform = platformFactory.createHoriMovingPlatform(0, yLoc);
        } else if (randDouble < HORIZONTAL_CHANCE + VERTICAL_CHANCE) {
            platform = platformFactory.createVertMovingPlatform(0, yLoc);
        } else if (randDouble < HORIZONTAL_CHANCE + VERTICAL_CHANCE + BREAK_CHANCE) {
            platform = platformFactory.createBreakPlatform(0, yLoc);
        }

        //TODO This prohibits platforms from being immutable
        int xLoc = (int) (widthDeviation * (serviceLocator.getConstants().getGameWidth() - platform.getHitBox()[AGameObject.HITBOX_RIGHT]));
        platform.setXPos(xLoc);

        return platform;
    }

    /**
     * Checks if the platform collides with any of the elements in the Block.
     *
     * @param platform The {@link IPlatform platform} that has to be checked for a collision
     * @param elements The {@link Set} in which the platforms should be placed
     * @return True if platform collides with one of the elements of block.
     */
    private boolean platformCollideCheck(final IPlatform platform, final Set<IGameObject> elements) {
        for (IGameObject e : elements) {
            if (platform.checkCollision(e)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a random powerup.
     *
     * @param elements A set of elements.
     * @param platform The platform a powerup potentially is placed on.
     **/
    private void chanceForPowerup(final Set<IGameObject> elements, final IPlatform platform) {
        ICalc calc = serviceLocator.getCalc();
        double randomDouble = calc.getRandomDouble(MAX_POWERUP_THRESHOLD);
        final int randomNr = (int) (randomDouble);

        double[] hitbox = platform.getHitBox();
        final int platformWidth = (int) hitbox[AGameObject.HITBOX_RIGHT];
        final int platformHeight = (int) hitbox[AGameObject.HITBOX_BOTTOM];
        boolean isSpecialPlatform = isSpecialPlatform(platform);

        if (randomNr >= SPRING_THRESHOLD && randomNr < TRAMPOLINE_THRESHOLD) {
            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            int springXLoc = (int) (calc.getRandomDouble(platformWidth));
            IGameObject powerup = powerupFactory.createSpring(0, 0);
            double[] powHitbox = powerup.getHitBox();
            final int powerupWidth = (int) powHitbox[AGameObject.HITBOX_RIGHT];

            int xPos = (int) platform.getXPos() + springXLoc;
            if (xPos > platform.getXPos() + platformWidth - powerupWidth) {
                xPos = xPos - powerupWidth;
            }
            powerup.setXPos(xPos);
            powerup.setYPos((int) platform.getYPos() - platformHeight + ITEM_Y_OFFSET);

            elements.add(powerup);
        } else if (randomNr >= TRAMPOLINE_THRESHOLD) {

            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            IGameObject powerup = powerupFactory.createTrampoline(
                    (int) platform.getXPos() + TRAMPOLINE_X_OFFSET,
                    (int) platform.getYPos() - platformHeight + ITEM_Y_OFFSET);
            elements.add(powerup);
        }
    }

    /**
     * Returns true if the platform has special properties, false otherwise.
     *
     * @param platform the platform that has to be checked.
     * @return true or false, dependent on the properties of the platform.
     */
    public static boolean isSpecialPlatform(final IPlatform platform) {
        Map<Platform.PlatformProperties, Integer> properties = platform.getProps();
        Platform.PlatformProperties[] keys = Platform.PlatformProperties.values();
        for (Platform.PlatformProperties key : keys) {
            if (properties.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

}
