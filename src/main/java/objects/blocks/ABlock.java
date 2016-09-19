package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodle;
import scenes.World;
import system.Game;
import system.IServiceLocator;

import java.util.HashSet;
import java.util.Set;

import static scenes.World.gravityAcceleration;

/* package */ abstract class ABlock extends AGameObject implements IBlock {

    /* package */ final Set<IGameObject> elements = new HashSet<>();
    /* package */ static IServiceLocator serviceLocator;
    /* package */ IPlatform topPlatform;

    /* package */ ABlock(IServiceLocator serviceLocator, int y) {
        super(0, y, null);
        ABlock.serviceLocator = serviceLocator;
    }

    /** {@inheritDoc} */
    @Override
    public Set<IGameObject> getElements() {
        return this.elements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addYPos(double y) {
        double current = this.getYPos();
        this.setYPos(current + y);

        for (IGameObject e : elements) {
            e.addYPos(y);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlatform getTopPlatform() {
        return topPlatform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        for (IGameObject e : elements) {
            e.render();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update() {
        cleanUpPlatforms();
        extraUpdate();
    }

    /**
     * Can be overriden by subclasses to extend the functionality of update
     */
    /* package */ void extraUpdate() {

    }

    /**
     * Place the platforms in the block.
     * @param lastPlatform The highest platform created before the block starts (normaly the latest platform created)
     * @param min The minimum amount of platforms to be created
     * @param max The maximum amount of platforms to be created
     */
    /* package */ final void placePlatforms(IPlatform lastPlatform, int min, int max) {
        lastPlatform = placeInitialPlatforms(lastPlatform);

        int platformAmount = serviceLocator.getCalc().getRandomIntBetween(min, max);
        int heightDividedPlatforms = Game.HEIGHT / platformAmount;

        lastPlatform = placeFollowingPlatformsChecked(lastPlatform, platformAmount, heightDividedPlatforms);

        this.setYPos(lastPlatform.getYPos());

        this.topPlatform = lastPlatform;
    }

    /**
     * <b>Overridable</b>
     * <br>
     * Places zero or more platforms before the loop placing the other platforms is processed.
     * @param lastPlatform The highest platform created before the block starts (normaly the latest platform created)
     * @return The last and highest platform created by this method
     */
    /* package */ IPlatform placeInitialPlatforms(IPlatform lastPlatform) {
        return lastPlatform;
    }

    private IPlatform placeFollowingPlatformsChecked(IPlatform lastPlatform, final int platformAmount, final int heightDividedPlatforms) {
        assert lastPlatform != null;
        assert platformAmount > 0;
        assert heightDividedPlatforms > 0;
        return placeFollowingPlatforms(lastPlatform, platformAmount, heightDividedPlatforms);
    }

    /**
     * <b>Overridable</b>
     * <br>
     * Executes the loop creating the platforms following the ones created by {@link #placeInitialPlatforms(IPlatform lastPlatform) placeInitialPlatforms}.
     * @param lastPlatform The highest platform created before the block starts (normaly the latest platform created)
     * @param platformAmount The amount of platforms to be created
     * @param heightDividedPlatforms The height between the platforms
     * @return The last and highest platform created by this method
     */
    /* package */ IPlatform placeFollowingPlatforms(IPlatform lastPlatform, final int platformAmount, final int heightDividedPlatforms) {
        for (int i = 0; i < platformAmount; i++) {
            lastPlatform = placeFollowingPlatform(lastPlatform, heightDividedPlatforms);
        }
        return lastPlatform;
    }

    /**
     * <b>Overridable</b>
     * <br>
     * Places a single platform part of the loop of {@link #placeFollowingPlatforms(IPlatform, int, int) placeFollowingPlatforms}.
     * @param lastPlatform The highest platform created before the block starts (normaly the latest platform created)
     * @param heightDividedPlatforms The height between the platforms
     * @return The last and highest platform created by this method
     */
    /* package */ IPlatform placeFollowingPlatform(final IPlatform lastPlatform, final int heightDividedPlatforms) {
        //TODO 1.7 and -0.8 are magic numbers
        double heightDeviation = serviceLocator.getCalc().getRandomDouble(1.7) - 0.8;
        double widthDeviation = serviceLocator.getCalc().getRandomDouble(1d);

        int yLast = (int) lastPlatform.getYPos();
        int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

        double jumpTime = lastPlatform.getBoost() / gravityAcceleration;
        double maxDoodleJumpHeight = (int) (0.5 * gravityAcceleration * Math.pow(jumpTime, 2));
        if (yLoc < yLast - maxDoodleJumpHeight) {
            yLoc = yLast - (int) maxDoodleJumpHeight;
        }

        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPlatform platform = platformFactory.createPlatform(0, yLoc);

        //TODO This prohibits platforms from being immutable
        int xLoc = (int) (widthDeviation * (Game.WIDTH - platform.getHitBox()[AGameObject.HITBOX_RIGHT]));
        platform.setXPos(xLoc);

        platformCollideCheck(platform);

        elements.add(platform);

        return platform;
    }

    /**
     * Checks if the platform collides with any of the platforms
     * in this Block. When there is a collision, delete the platform
     * from the list.
     * @param platform The IPlatform that has to be checked for collision.
     */
    private void platformCollideCheck(IPlatform platform) {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for(IGameObject e : elements){
            if (platform.checkCollission(e)) {
                toRemove.add(e);
            }
        }

        for(IGameObject e : toRemove) {
            elements.remove(e);
        }
    }

    /**
     * Checks for all the Platforms if they are under over the height
     * of the screen, if that's the case, delete that Platforms.
     */
    private void cleanUpPlatforms() {
        Set<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : elements) {
            if (e.getYPos() - 50 > Game.HEIGHT) {
                toRemove.add(e);
            }
        }

        for (IGameObject e : toRemove) {
            elements.remove(e);
        }
    }
}
