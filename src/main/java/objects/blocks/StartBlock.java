package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import scenes.World;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * This class focusses on the implementation of Blocks.
 * These blocks contain the main bulk of the game objects.
 * This bulk contains the platforms, powerups, enemies and other interactable items.
 * These blocks are meant to pass through our frame vertically.
 * The player is meant to progress from one block to the next by jumping on things.
 * These things can be anything as specified by "bulk".
 * The choice for block was made as to make seperate sub-levels in a continuous world.
 */
public class StartBlock extends AGameObject implements IBlock {

    /**
     * Used to gain access to all services.
     */
    private static IServiceLocator serviceLocator;
    /**
     * A sorted list of all the game objects in this block.
     */
    private ArrayList<IGameObject> content = new ArrayList<>();
    /**
     * This is only to be sure a block has a certain height.
     * After this the block will be
     * dynamic to the last element added to the list
     */
    private final int constructionOffset = 800;

    /**
     * The maximum amount of platforms per block.
     */
    private final int maxPlatforms = 13;

    /**
     * The minimum amount of platforms per block.
     */
    private final int minPlatforms = 6;

    /**
     * Offset to clean up platforms upon leaving the screen.
     */
    private final double cleanupOffset = 0.01;

    /**
     * A multiplier to generate a proper height deviation.
     */
    private final double heightDeviationMultiplier = 1.7;

    /**
     * An offset to generate a minimum height deviation.
     */
    private final double heightDeviationOffset = 0.8;

    /**
     * Create a start block.
     * @param sL the service locator.
     */
    StartBlock(final IServiceLocator sL) {
        StartBlock.serviceLocator = sL;

        setYPos(0);

        createAndPlaceObjects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render() {
        for (IGameObject e : content) {
            e.render();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animate() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update() {
        for (IGameObject e : content) {
            e.update();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ArrayList<IGameObject> getContent() {
        return this.content;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void placePlatforms(IGameObject lastObject) {
        int max = maxPlatforms;
        int min = minPlatforms;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = (int) Game.HEIGHT / platformAmount;

        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPlatform platform = platformFactory.createPlatform(Game.WIDTH / 2, (int) (Game.HEIGHT));
        lastObject = platform;
        content.add(platform);

        double t = World.vSpeedLimit / World.gravityAcceleration;

        int maxY = (int) (World.gravityAcceleration * Math.pow(t, 2) / 2);

        for (int i = 1; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * heightDeviationMultiplier - heightDeviationOffset);
            float widthDeviation = (float) (rand.nextFloat());

            int yLast = (int) lastObject.getYPos();

            int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

            if (yLoc < yLast - maxY) {
                yLoc = yLast - maxY;
            }

            int xLoc = (int) (widthDeviation * Game.WIDTH);
            platform = platformFactory.createPlatform(xLoc, yLoc);


            platformCollideCheck(platform);

            content.add(platform);
            lastObject = platform;
        }

        this.setYPos(lastObject.getYPos());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addYPos(final double y) {
        double current = this.getYPos();
        this.setYPos(current + y);

        for (IGameObject e : content) {
            e.addYPos(y);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void cleanUpPlatforms() {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : content) {
            //A marge of 50 is used
            if (e.getYPos() + Game.HEIGHT * cleanupOffset > Game.HEIGHT) {
                toRemove.add(e);
            }

        }
        for (IGameObject e : toRemove) {
            content.remove(e);
        }
    }

    /**
     * Creates and places platforms in the block.
     */
    private void createAndPlaceObjects() {
        placePlatforms(null);
    }

    /**
     * Checks if the platform collides with any of the platforms
     * in this Block. When there is a collision, delete the platform
     * from the list.
     *
     * @param platform The IPlatform that has to be checked for collision.
     */
    private void platformCollideCheck(final IPlatform platform) {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : content) {
            if (serviceLocator.getCollisions().collide(platform, e)) {
                toRemove.add(e);
            }
        }

        for (IGameObject e : toRemove) {
            content.remove(e);
        }
    }

}
