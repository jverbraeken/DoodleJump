package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import system.Game;
import system.IServiceLocator;

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
    public IBlock createStartBlock() {
        IBlock block = new Block(serviceLocator);

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(6, (Game.WIDTH + Game.HEIGHT) / 130);
        int heightDividedPlatforms = Game.HEIGHT / platformAmount;

        IPlatform lastJumpable = placeInitialStartBlockPlatforms(block);
        placeStartBlockPlatforms(block, lastJumpable, platformAmount, heightDividedPlatforms);
        return block;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBlock createBlock(IJumpable lastPlatform) {
        IBlock block = new Block(serviceLocator);

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(8, (Game.WIDTH + Game.HEIGHT) / 120);
        int heightDividedPlatforms = Game.HEIGHT / platformAmount;

        placeBlockPlatforms(block, lastPlatform, platformAmount, heightDividedPlatforms);


        return block;
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param block The block in which the platforms must be placed
     * @param lastJumpable The highest platform created before the block starts (normaly the latest platform created)
     * @return The last and highest platform created by this method
     */
    private IJumpable placeBlockPlatforms(final IBlock block, IJumpable lastJumpable, final int platformAmount, final int heightDividedPlatforms) {
        assert lastJumpable != null;
        assert platformAmount > 0;
        assert heightDividedPlatforms > 0;
        for (int i = 0; i < platformAmount; i++) {
            lastJumpable = placeFollowingPlatform(block, lastJumpable, heightDividedPlatforms);
        }
        return lastJumpable;
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param block The block in which the platforms must be placed
     * @return The last and highest platform created by this method
     */
    private IPlatform placeInitialStartBlockPlatforms(final IBlock block) {
        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        //TODO 1.2 is a magic number
        IPlatform platform = platformFactory.createPlatform(Game.WIDTH / 2, (int) (Game.HEIGHT / 1.2));
        block.addElement(platform);
        return platform;
    }

    /**
     * Places zero or more platforms before the loop placing the other platforms is processed.
     *
     * @param block The block in which the platforms must be placed
     * @param lastPlatform The highest platform created before the block starts (normaly the latest platform created)
     * @return The last and highest platform created by this method
     */
    private IPlatform placeStartBlockPlatforms(final IBlock block, IPlatform lastPlatform, final int platformAmount, final int heightDividedPlatforms) {
        assert lastPlatform != null;
        assert platformAmount > 0;
        assert heightDividedPlatforms > 0;
        for (int i = 1; i < platformAmount; i++) {
            lastPlatform = placeFollowingPlatform(block, lastPlatform, heightDividedPlatforms);
        }
        return lastPlatform;
    }

    /**
     * Places a single platform part in the block specified.
     *
     * @param lastJumpable           The highest platform created before the block starts (normaly the latest platform created)
     * @param heightDividedPlatforms The height between the platforms
     * @return The last and highest platform created by this method
     */
    private IPlatform placeFollowingPlatform(final IBlock block, final IJumpable lastJumpable, final int heightDividedPlatforms) {
        //TODO 1.7 and -0.8 are magic numbers
        double heightDeviation = serviceLocator.getCalc().getRandomDouble(1.7) - 0.8;
        double widthDeviation = serviceLocator.getCalc().getRandomDouble(1d);

        int yLast = (int) lastJumpable.getYPos();
        int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

        double jumpTime = lastJumpable.getBoost() / gravityAcceleration;
        double maxDoodleJumpHeight = (int) (0.5 * gravityAcceleration * Math.pow(jumpTime, 2));
        if (yLoc < yLast - maxDoodleJumpHeight) {
            yLoc = yLast - (int) maxDoodleJumpHeight;
        }

        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPlatform platform = platformFactory.createPlatform(0, yLoc);

        //TODO This prohibits platforms from being immutable
        int xLoc = (int) (widthDeviation * (Game.WIDTH - platform.getHitBox()[AGameObject.HITBOX_RIGHT]));
        platform.setXPos(xLoc);

        while (platformCollideCheck(block, platform)) {
            platform = platformFactory.createPlatform(0, yLoc);

            //TODO This prohibits platforms from being immutable
            xLoc = (int) (widthDeviation * (Game.WIDTH - platform.getHitBox()[AGameObject.HITBOX_RIGHT]));
            platform.setXPos(xLoc);
        }

        block.addElement(platform);

        return platform;
    }

    /**
     * Checks if the platform collides with any of the platforms
     * in this Block.
     *
     * @param block    The {@link IBlock block} that contains the elements to check for a collision
     * @param platform The {@link IPlatform platform} that has to be checked for a collision
     * @return True if platformm collides with one of the elements of block
     */
    private boolean platformCollideCheck(final IBlock block, IPlatform platform) {
        for (IGameObject e : block.getElements()) {
            if (platform.checkCollission(e)) {
                return true;
            }
        }
        return false;
    }

}
