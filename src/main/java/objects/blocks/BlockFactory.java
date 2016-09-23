package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.powerups.IPowerupFactory;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is the factory in which seperate blocks get created.
 * In here one can specify the type of block one wants to create.
 */
public final class BlockFactory implements IBlockFactory {
    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;

    /**
     * Register the block factory into the service locator.
     * @param sL the service locator.
     */
    public static void register(final IServiceLocator sL) {
        assert serviceLocator != null;
        BlockFactory.serviceLocator = sL;
        BlockFactory.serviceLocator.provide(new BlockFactory());
    }

    /**
     * This is only to be sure a block has a certain height.
     * After this the block will be
     * dynamic to the last element added to the list
     */
    private final int constructionOffset = 800;

    /**
     * The maximum amount of platforms per block.
     */
    private final int maxPlatforms = 10;

    /**
     * The minimum amount of platforms per block.
     */
    private final int minPlatforms = 4;

    /**
     * Offset to clean up platforms upon leaving the screen.
     */
    private final double cleanupOffset = 0.01;

    /**
     * Offset to place the trampoline on the proper place of a platform.
     */
    private final int itemYoffset = 5;

    /**
     * Offset to place the trampoline on the proper place of a platform.
     */
    private final int trampolineXoffset = 20;

    /**
     * Threshold in order to spawn a trampoline.
     * random int(10.000 > 9900)
     */
    private final int trampolineThreshold = 9900;

    /**
     * Threshold in order to spawn a trampoline.
     * random int(9500 < x < 9900)
     */
    private final int springThreshold = 9500;

    /**
     * Total threshold number for item generation.
     * random int(10000)
     */
    private final int maxPowerupThreshold = 10000;

    /**
     * A multiplier to generate a proper height deviation.
     */
    private final double heightDeviationMultiplier = 1.7;

    /**
     * An offset to generate a minimum height deviation.
     */
    private final double heightDeviationOffset = 0.8;

    /**
     * Prevent instantiations of BlockFactory.
     */
    private BlockFactory() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized IBlock createStartBlock() {
        Set<IGameObject> elements = new HashSet<>();

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(6, (serviceLocator.getConstants().getGameWidth() + serviceLocator.getConstants().getGameHeight()) / 130);
        int heightDividedPlatforms = serviceLocator.getConstants().getGameHeight() / platformAmount;

        IPlatform topJumpable = placeInitialStartBlockPlatforms(elements);
        topJumpable = placeStartBlockPlatforms(elements, topJumpable, platformAmount, heightDividedPlatforms);
        return new Block(serviceLocator, elements, topJumpable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized IBlock createBlock(final IJumpable topJumpable) {
        Set<IGameObject> elements = new HashSet<>();

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(minPlatforms, maxPlatforms);
        int heightDividedPlatforms = serviceLocator.getConstants().getGameHeight() / platformAmount;

        IJumpable newTopJumpable = placeBlockPlatforms(elements, topJumpable, platformAmount, heightDividedPlatforms);


        return new Block(serviceLocator, elements, newTopJumpable);
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements    The {@link Set} in which the platforms should be placed
     * @param topJumpable The highest platform created before the block starts (normaly the latest platform created)
     * @return The last and highest platform created by this method
     */
    private IJumpable placeBlockPlatforms(final Set<IGameObject> elements, final IJumpable topJumpable, final int platformAmount, final int heightDividedPlatforms) {
        assert topJumpable != null;
        assert platformAmount > 0;
        assert heightDividedPlatforms > 0;
        IJumpable newtopJumpable = topJumpable;
        for (int i = 0; i < platformAmount; i++) {
            newtopJumpable = placeFollowingPlatform(elements, newtopJumpable, heightDividedPlatforms);
        }
        return newtopJumpable;
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements The {@link Set} in which the platforms should be placed
     * @return The last and highest platform created by this method
     */
    private IPlatform placeInitialStartBlockPlatforms(final Set<IGameObject> elements) {
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        //TODO 1.2 is a magic number
        IPlatform platform = platformFactory.createPlatform(serviceLocator.getConstants().getGameWidth() / 2, (int) (serviceLocator.getConstants().getGameHeight() / 1.2));
        elements.add(platform);
        return platform;
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements    The {@link Set} in which the platforms should be placed
     * @param topJumpable The highest platform created before the block starts (normaly the latest platform created)
     * @return The last and highest platform created by this method
     */
    private IPlatform placeStartBlockPlatforms(final Set<IGameObject> elements, IPlatform topJumpable, final int platformAmount, final int heightDividedPlatforms) {
        assert topJumpable != null;
        assert platformAmount > 0;
        assert heightDividedPlatforms > 0;
        for (int i = 1; i < platformAmount; i++) {
            topJumpable = placeFollowingPlatform(elements, topJumpable, heightDividedPlatforms);
        }
        return topJumpable;
    }

    /**
     * Places a single platform part in the block specified.
     *
     * @param topJumpable            The highest platform created before the block starts (normaly the latest platform created)
     * @param heightDividedPlatforms The height between the platforms
     * @return The last and highest platform created by this method
     */
    private IPlatform placeFollowingPlatform(final Set<IGameObject> elements, final IJumpable topJumpable, final int heightDividedPlatforms) {
        IPlatform platform;
        do {
            platform = makeFollowingPlatform(topJumpable, heightDividedPlatforms);
        } while (platformCollideCheck(platform, elements));

        chanceForPowerup(elements, platform);
        elements.add(platform);
        return platform;
    }

    private IPlatform makeFollowingPlatform(final IJumpable topJumpable, final int heightDividedPlatforms) {
        //TODO 1.7 and -0.8 are magic numbers
        double heightDeviation = serviceLocator.getCalc().getRandomDouble(heightDeviationMultiplier) - heightDeviationOffset;
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
     * @return True if platformm collides with one of the elements of block
     */
    private boolean platformCollideCheck(final IPlatform platform, final Set<IGameObject> elements) {
        for (IGameObject e : elements) {
            if (platform.checkCollission(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes a random number between 0 and 10000 and
     * gives the platform a powerup if it's a certain number
     * between 0 and 10000.
     *
     * @param platform The platform a powerup potentially is placed on.
     **/
    private void chanceForPowerup(final Set<IGameObject> elements, final IPlatform platform) {

        final int randomNr = (int) (serviceLocator.getCalc().getRandomDouble(maxPowerupThreshold));

        final int platformWidth = (int) platform.getHitBox()[AGameObject.HITBOX_RIGHT];
        final int platformHeight = (int) platform.getHitBox()[AGameObject.HITBOX_BOTTOM];

        if (randomNr < springThreshold) {
            return;
        } else if (randomNr >= springThreshold && randomNr < trampolineThreshold) {
            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            int springXLoc = (int) (serviceLocator.getCalc().getRandomDouble(platformWidth));
            IGameObject powerup = powerupFactory.createSpring(0, 0);
            final int powerupWidth = (int) powerup.getHitBox()[AGameObject.HITBOX_RIGHT];

            int xPos = (int) platform.getXPos() + springXLoc;
            if (xPos > platform.getXPos() + platformWidth - powerupWidth) {
                xPos = xPos - powerupWidth;
            }
            powerup.setXPos(xPos);
            powerup.setYPos((int) platform.getYPos() - platformHeight + itemYoffset);

            elements.add(powerup);
        } else if (randomNr >= trampolineThreshold) {

            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            IGameObject powerup = powerupFactory.createTrampoline((int) platform.getXPos() + trampolineXoffset,
                    (int) platform.getYPos() - platformHeight + itemYoffset);
            elements.add(powerup);
        }
    }

}
