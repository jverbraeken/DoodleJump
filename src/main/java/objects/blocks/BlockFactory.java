package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import system.Game;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Set;

import static scenes.World.gravityAcceleration;

public final class BlockFactory implements IBlockFactory {

    private static transient IServiceLocator serviceLocator;

    /**
     * Prevent instantiations of BlockFactory.
     */
    private BlockFactory() {
    }

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        BlockFactory.serviceLocator = serviceLocator;
        serviceLocator.provide(new BlockFactory());
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

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(8, (serviceLocator.getConstants().getGameWidth() + serviceLocator.getConstants().getGameHeight()) / 120);
        int heightDividedPlatforms = serviceLocator.getConstants().getGameHeight() / platformAmount;

        IJumpable newTopJumpable = placeBlockPlatforms(elements, topJumpable, platformAmount, heightDividedPlatforms);


        return new Block(serviceLocator, elements, newTopJumpable);
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param elements      The {@link Set} in which the platforms should be placed
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
     * @param elements The {@link Set} in which the platforms should be placed
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
     * @param topJumpable           The highest platform created before the block starts (normaly the latest platform created)
     * @param heightDividedPlatforms The height between the platforms
     * @return The last and highest platform created by this method
     */
    private IPlatform placeFollowingPlatform(final Set<IGameObject> elements, final IJumpable topJumpable, final int heightDividedPlatforms) {
        IPlatform platform;
        do {
            platform = makeFollowingPlatform(topJumpable, heightDividedPlatforms);
        } while (platformCollideCheck(platform, elements));

        elements.add(platform);
        return platform;
    }

    private IPlatform makeFollowingPlatform(final IJumpable topJumpable, final int heightDividedPlatforms) {
        //TODO 1.7 and -0.8 are magic numbers
        double heightDeviation = serviceLocator.getCalc().getRandomDouble(1.7) - 0.8;
        double widthDeviation = serviceLocator.getCalc().getRandomDouble(1d);

        int yLast = (int) topJumpable.getYPos();
        int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

        double jumpTime = topJumpable.getBoost() / gravityAcceleration;
        double maxDoodleJumpHeight = (int) (0.5 * gravityAcceleration * Math.pow(jumpTime, 2));
        if (yLoc < yLast - maxDoodleJumpHeight) {
            yLoc = yLast - (int) maxDoodleJumpHeight;
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

}
