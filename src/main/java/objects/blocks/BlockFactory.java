package objects.blocks;

import math.GenerationSet;
import math.ICalc;
import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.blocks.platform.Platform;
import objects.enemies.Enemies;
import objects.powerups.IPowerup;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static objects.blocks.ElementTypes.horizontalMovingPlatform;
import static objects.blocks.ElementTypes.normalPlatform;
import static objects.blocks.ElementTypes.randomPlatform;
import static objects.blocks.ElementTypes.verticalMovingPlatform;
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
    private static final double ENEMY_CHANCE = 700;

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

        final double initialPlatformHeightDivider = 200d;
        final int initialPlatforms = 6;
        Set<IGameObject> elements = new HashSet<>();
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();

        IPlatform platform = platformFactory.createPlatform(0, 0);
        for (int i = 0; i < initialPlatforms; i++) {
            IPlatform newPlatform = platformFactory.createPlatform(
                    i * serviceLocator.getConstants().getGameWidth() / initialPlatforms,
                    (int) (serviceLocator.getConstants().getGameHeight() - initialPlatformHeightDivider));
            elements.add(newPlatform);
            platform = newPlatform;
        }

        return new Block(elements, platform);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized IBlock createBlock(final IJumpable topJumpable, final BlockTypes type, final boolean enemies) throws RuntimeException {
        switch (type) {
            case standardBlock:
                return createTypeOnlyBlock(topJumpable, randomPlatform, enemies);
            case normalOnlyBlock:
                return createTypeOnlyBlock(topJumpable, normalPlatform, enemies);
            case horizontalOnlyBlock:
                return createTypeOnlyBlock(topJumpable, horizontalMovingPlatform, enemies);
            case verticalOnlyBlock:
                return createTypeOnlyBlock(topJumpable, verticalMovingPlatform, enemies);
            default:
                throw new RuntimeException(type + "is an unknown block type to the BlockFactory");
        }
    }

    /**
     * Create a block with the specified platform types.
     *
     * @param topJumpable the current top platform.
     * @param type        the type of platforms to be put into the block.
     * @return a block containing platforms of the type.
     */
    private synchronized IBlock createTypeOnlyBlock(final IJumpable topJumpable, final ElementTypes type, final boolean enemy) {

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(MIN_PLATFORMS, MAX_PLATFORMS);
        int heightDividedPlatforms = serviceLocator.getConstants().getGameHeight() / platformAmount;
        Set<IGameObject> elements = new HashSet<>();
        IJumpable newTopJumpable = topJumpable;

        for (int i = 0; i < platformAmount; i++) {

            IPlatform platform;
            boolean breaking;
            do {
                do {
                    platform = createPlatform(newTopJumpable, heightDividedPlatforms, type);
                } while (verifyPlatformLocation(platform, elements));
                elements.add(platform);
                Platform.PlatformProperties br = breaks;
                breaking = platform.getProps().containsKey(br);
            } while (breaking);
            newTopJumpable = platform;
            chanceForPowerup(elements, platform);
            if (enemy) {
                spawnEnemyWithChance(elements, (IPlatform) newTopJumpable, heightDividedPlatforms);
            }
        }

        return new Block(elements, newTopJumpable);
    }

    /**
     * Create a platform on top above the topJumpable.
     *
     * @param topJumpable            the current highest platform.
     * @param heightDividedPlatforms the deviation to the next platform.
     * @param type                   the type of the to be made platform.
     * @return a new platform.
     */
    private IPlatform createPlatform(final IJumpable topJumpable, final int heightDividedPlatforms, final ElementTypes type) {
        IPlatform platform = serviceLocator.getPlatformFactory().createPlatform(type);

        double heightDeviation = serviceLocator.getCalc().getRandomDouble(2d) - 1;
        double widthDeviation = serviceLocator.getCalc().getRandomDouble(1d);

        int yLast = (int) topJumpable.getYPos();
        int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms) / 2);
        int xLoc = (int) (widthDeviation * (serviceLocator.getConstants().getGameWidth() - 2 * platform.getHitBox()[AGameObject.HITBOX_RIGHT]) + platform.getHitBox()[AGameObject.HITBOX_RIGHT]);

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
        IGameObject enemy;

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

        return serviceLocator.getEnemyFactory().createEnemy(Enemies.ordinaryMonster, xLoc, yLoc);
    }
}
