package objects.blocks;

import math.GenerationSet;
import math.ICalc;
import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.blocks.platform.Platform;
import objects.powerups.IPowerup;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static objects.blocks.ElementTypes.*;
import static objects.blocks.platform.Platform.PlatformProperties.breaks;


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
     * The chance that an enemy will spawn.
     */
    private static final double ENEMY_CHANCE = 9700;

    /**
     * Total threshold number for item generation.
     * random int(10000)
     */
    private static final int MAX_RANDOM_THRESHOLD = 10000;
    /**
     * A multiplier to generate a proper height deviation.
     */
    private static final double HEIGHT_DEVIATION_MULTIPLIER = 1.5;
    /**
     * An offset to generate a minimum height deviation.
     */
    private static final double HEIGHT_DEVIATION_OFFSET = .8;
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    /**
     * A weighted set for the spawning of powerups.
     */
    private GenerationSet powerupGenerationSet;

    /**
     * Initialize the BlockFactory.
     */
    private BlockFactory() {
        powerupGenerationSet = new GenerationSet(serviceLocator, "powerups");
    }

    /**
     * Register the block factory into the service locator.
     *
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        if (sL == null) {
            throw new IllegalArgumentException("The service locator cannot be null");
        }
        BlockFactory.serviceLocator = sL;
        BlockFactory.serviceLocator.provide(new BlockFactory());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized IBlock createStartBlock() {
        Set<IGameObject> elements = new HashSet<>();
        IPlatform topJumpable = placeInitialStartBlockPlatforms(elements);
        return new Block(elements, topJumpable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized IBlock createBlock(final IJumpable topJumpable, BlockTypes type) throws RuntimeException {
        Set<IGameObject> elements = new HashSet<>();

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(MIN_PLATFORMS, MAX_PLATFORMS);

        IJumpable newTopJumpable = topJumpable;

        switch(type) {
            case standardBlock:
                return createTypeOnlyBlock(elements, topJumpable, platformAmount, randomPlatform);
            case normalOnlyBlock:
                return createTypeOnlyBlock(elements, topJumpable, platformAmount, normalPlatform);
            case horizontalOnlyBlock:
                return createTypeOnlyBlock(elements, topJumpable, platformAmount, horizontalMovingPlatform);
            case verticalOnlyBlock:
                return createTypeOnlyBlock(elements, topJumpable, platformAmount, verticalMovingPlatform);
            default:
                throw new RuntimeException("We exceeded the list in blockTypes somehow.");
        }
    }

    private synchronized IBlock createTypeOnlyBlock(Set<IGameObject> elements, IJumpable topJumpable, int platformAmount, ElementTypes type) {
        int heightDividedPlatforms = serviceLocator.getConstants().getGameHeight() / platformAmount;
        Set<IGameObject> newElements = elements;
        IJumpable newTopJumpable = topJumpable;

        for (int i = 0; i < platformAmount; i++) {

            IPlatform platform;
            boolean breaking;
            do {
                do {
                        platform = createPlatform(newTopJumpable, heightDividedPlatforms, type);
                    } while (verifyPlatformLocation(platform, newElements));
                elements.add(platform);
                Platform.PlatformProperties br = breaks;
                breaking = platform.getProps().containsKey(br);
            } while (breaking);
            newTopJumpable = platform;
            chanceForPowerup(elements, platform);
        }

        return new Block(newElements, newTopJumpable);
    }

    public IPlatform createPlatform(IJumpable topJumpable, int heightDividedPlatforms, ElementTypes type) {
        IPlatform platform = serviceLocator.getPlatformFactory().createPlatform(type);

        double heightDeviation = serviceLocator.getCalc().getRandomDouble(2d) - 1;
        double widthDeviation = serviceLocator.getCalc().getRandomDouble(1d);

        int yLast = (int) topJumpable.getYPos();
        int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms)/2);
        int xLoc = (int) (widthDeviation * (serviceLocator.getConstants().getGameWidth() - 2* platform.getHitBox()[AGameObject.HITBOX_RIGHT]) + platform.getHitBox()[AGameObject.HITBOX_RIGHT]);

        double jumpTime = topJumpable.getBoost() / serviceLocator.getConstants().getGravityAcceleration();
        double maxY = (int) (serviceLocator.getConstants().getGravityAcceleration() * Math.pow(jumpTime, 2) / 2);
        if (yLoc < yLast - maxY) {
            yLoc = yLast - (int) maxY;
        }

        platform.setYPos(yLoc);
        platform.setXPos(xLoc);

        return platform;

    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements               The {@link Set} in which the platforms should be placed.
     * @param topJumpable            The highest platform created before the block starts (normally the latest
     *                               platform created).
     * @param platformAmount         The amount of platforms.
     * @return The last and highest platform created by this method
     */
    /*private Block placeBlockPlatforms(final Set<IGameObject> elements, final IJumpable topJumpable, final int platformAmount) {
        assert topJumpable != null;
        assert platformAmount > 0;
        int heightDividedPlatforms = serviceLocator.getConstants().getGameHeight() / platformAmount;

        IJumpable newTopJumpable = topJumpable;
        for (int i = 0; i < platformAmount; i++) {
            newTopJumpable = placeFollowingPlatform(elements, newTopJumpable, heightDividedPlatforms);
            //spawnEnemyWithChance(elements, (IPlatform) newTopJumpable, heightDividedPlatforms);
        }
        return new Block(elements, newTopJumpable);
    }*/

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements The {@link Set} in which the platforms should be placed
     * @return The last and highest platform created by this method
     */
    private IPlatform placeInitialStartBlockPlatforms(Set<IGameObject> elements) {
        final double initialPlatformHeightDivider = 200d;
        final int initialPlatforms = 6;
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();

        IPlatform platform = platformFactory.createPlatform(0,0);
        for(int i = 0; i < initialPlatforms; i++) {
            IPlatform newPlatform = platformFactory.createPlatform(
                    i * serviceLocator.getConstants().getGameWidth() / initialPlatforms,
                    (int) (serviceLocator.getConstants().getGameHeight() - initialPlatformHeightDivider));
            elements.add(newPlatform);
            platform = newPlatform;
        }

        return platform;
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements               The {@link Set} in which the platforms should be placed.
     * @param topJumpable            The highest platform created before the block starts (normally the latest
     *                               platform created).
     * @param platformAmount         The amount of platforms.
     * @param heightDividedPlatforms The height division of platforms (?).
     * @return The last and highest platform created.
     */
    /*private IPlatform placeStartBlockPlatforms(final Set<IGameObject> elements, final IPlatform topJumpable, final int platformAmount, final int heightDividedPlatforms) {
        assert topJumpable != null;
        assert platformAmount > 0;
        assert heightDividedPlatforms > 0;

        IPlatform highest = placeFollowingPlatform(elements, topJumpable, heightDividedPlatforms);
        for (int i = 2; i < platformAmount; i++) {
            highest = placeFollowingPlatform(elements, highest, heightDividedPlatforms);
        }
        return highest;
    }*/

    /**
     * Places a single platform part in the block specified.
     *
     * @param elements           A set of elements.
     * @param topJumpable        The highest platform created before the block starts (normally the latest platform created)
     * @param heightDivPlatforms The height between the platforms
     * @return The last and highest platform created by this method
     */
    /*private IPlatform placeFollowingPlatform(final Set<IGameObject> elements, final IJumpable topJumpable, final int heightDivPlatforms) {
        IPlatform platform;
        boolean breaks;
        do {
            do {
                platform = makeFollowingPlatform(topJumpable, heightDivPlatforms);
            } while (verifyPlatformLocation(platform, elements));
            elements.add(platform);
            Platform.PlatformProperties br = Platform.PlatformProperties.breaks;
            breaks = platform.getProps().containsKey(br);
        } while (breaks);

        chanceForPowerup(elements, platform);
        return platform;
    }*/

    /**
     * Make a platform following the last platform.
     *
     * @param topJumpable            The last jumpable object.
     * @param heightDividedPlatforms The height division for platforms.
     * @return A new platform.
     */
    /*private IPlatform makeFollowingPlatform(final IJumpable topJumpable, final int heightDividedPlatforms) {
        double heightDeviation = serviceLocator.getCalc().getRandomDouble(HEIGHT_DEVIATION_MULTIPLIER) - HEIGHT_DEVIATION_OFFSET;
        double widthDeviation = serviceLocator.getCalc().getRandomDouble(1d);

        int yLast = (int) topJumpable.getYPos();
        int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

        double jumpTime = topJumpable.getBoost() / serviceLocator.getConstants().getGravityAcceleration();
        double maxY = (int) (serviceLocator.getConstants().getGravityAcceleration() * Math.pow(jumpTime, 2) / 2);
        if (yLoc < yLast - maxY) {
            yLoc = yLast - (int) maxY;
        }

        IPlatform platform = (IPlatform) platformGenerationSet.getRandomElement();
        platform.setYPos(yLoc);
        int xLoc = (int) (widthDeviation * (serviceLocator.getConstants().getGameWidth() - platform.getHitBox()[AGameObject.HITBOX_RIGHT]));
        platform.setXPos(xLoc);

        return platform;
    }*/

    /**
     * Checks if the platform collides with any of the elements in the Block.
     *
     * @param gameObject The {@link IGameObject object} that has to be checked for a collision
     * @param elements   The {@link Set} in which the platforms should be placed
     * @return True if platform collides with one of the elements of block.
     */
    private boolean verifyPlatformLocation(final IGameObject gameObject, final Set<IGameObject> elements) {
        for (IGameObject e : elements) {
            if (gameObject.checkCollision(e)) {
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
        if (!isSpecialPlatform(platform)) {
            IPowerup powerup = (IPowerup) powerupGenerationSet.getRandomElement();
            if (powerup != null) {
                powerup.setPositionOnPlatform(platform);
                elements.add(powerup);
            }
        }
    }

    /**
     * Creates a random Enemy.
     *
     * @param elements               A set of elements.
     * @param platform               The platform a powerup potentially is placed on.
     * @param heightDividedPlatforms the height devided by the amount of platforms.
     **/
    private void spawnEnemyWithChance(final Set<IGameObject> elements, final IPlatform platform, final int heightDividedPlatforms) {
        ICalc calc = serviceLocator.getCalc();
        double randomDouble = calc.getRandomDouble(MAX_RANDOM_THRESHOLD);
        final int randomNr = (int) (randomDouble);
        IGameObject enemy = null;

        if (randomNr >= ENEMY_CHANCE) {
            do {
                enemy = placeEnemy(platform, heightDividedPlatforms);
            } while (verifyPlatformLocation(enemy, elements));
            elements.add(enemy);
        }
    }

    /**
     * Create the enemy at the given location and return it.
     *
     * @param platform               the last platform.
     * @param heightDividedPlatforms the height devided by the amount of platforms.
     * @return the Enemy as IGameObject.
     */
    private IGameObject placeEnemy(final IPlatform platform, final int heightDividedPlatforms) {
        ICalc calc = serviceLocator.getCalc();
        double widthDeviation = calc.getRandomDouble(1d);
        double heightDeviation = calc.getRandomDouble(HEIGHT_DEVIATION_MULTIPLIER) - HEIGHT_DEVIATION_OFFSET;

        int xLoc = (int) (widthDeviation * (serviceLocator.getConstants().getGameWidth() - platform.getHitBox()[AGameObject.HITBOX_RIGHT]));
        int yLoc = (int) (platform.getYPos() - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

        return serviceLocator.getEnemyFactory().createOrdinaryEnemy(xLoc, yLoc);
    }
}
