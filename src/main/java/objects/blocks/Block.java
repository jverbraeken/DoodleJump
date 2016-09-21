package objects.blocks;

import objects.AGameObject;
import objects.IGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.powerups.IPowerupFactory;
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
class Block extends AGameObject implements IBlock {

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
    private final int constructionOFFSET = 800;

    /**
     * The maximum amount of platforms per block.
     */
    private final int maxPLATFORMS = 13;

    /**
     * The minimum amount of platforms per block.
     */
    private final int minPLATFORMS = 4;

    /**
     * Offset to clean up platforms upon leaving the screen.
     */
    private final double cleanupOFFSET = 0.01;

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
    private final int tramolineTHRESHOLD = 9900;

    /**
     * Threshold in order to spawn a trampoline.
     * random int(9500 < x < 9900)
     */
    private final int springTHRESHOLD = 9500;

    /**
     * Total threshold number for item generation.
     * random int(10000)
     */
    private final int maxTHRESHOLD = 10000;

    /**
     * A multiplier to generate a proper height deviation.
     */
    private final double heightDeviationMultiplier = 1.7;

    /**
     * An offset to generate a minimum height deviation.
     */
    private final double heightDeviationOffset = 0.8;

    /**
     * The constructor of a block.
     * @param sL the Service locator needed for factories.
     * @param lastObject the object from where the new block should start building.
     */
    Block(final IServiceLocator sL, final IGameObject lastObject) {
        Block.serviceLocator = sL;

        //This is only to be sure it has a certain height, after this it will be
        //dynamic to the last element added to the list
        setYPos(lastObject.getYPos() + constructionOFFSET);
        createAndPlaceObjects(lastObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
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
    public void update() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addYPos(final double y) {
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
    public ArrayList<IGameObject> getContent() {
        return this.content;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placePlatforms(IGameObject lastObject) {
        int max = maxPLATFORMS;
        int min = minPLATFORMS;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;

        int heightDividedPlatforms = (int) Game.HEIGHT / platformAmount;

        double t = World.vSpeedLimit / World.gravityAcceleration;

        int maxY = (int) (World.gravityAcceleration * Math.pow(t, 2) / 2);

        for (int i = 0; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * heightDeviationMultiplier - heightDeviationOffset);
            float widthDeviation = rand.nextFloat();

            int yLast = (int) lastObject.getYPos();
            int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));


            if (yLoc < yLast - maxY) {
                yLoc = yLast - maxY;
            }

            IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
            IPlatform platform = platformFactory.createPlatform(0, yLoc);

            int xLoc = (int) (widthDeviation * (Game.WIDTH - platform.getWidth()));
            platform.setXPos(xLoc);

            platformCollideCheck(platform);

            content.add(platform);

            lastObject = platform;

            chanceForPowerup(platform);

        }

        this.setYPos(lastObject.getYPos());
    }

    /**
     * Takes a random number between 0 and 10000 and
     * gives the platform a powerup if it's a certain number
     * between 0 and 10000.
     *
     * @param platform The platform a powerup potentially is placed on.
     **/
    private void chanceForPowerup(final IPlatform platform) {
        //TODO use serviceLocator
        Random rand = new Random();

        int randomNr = (int) (rand.nextFloat() * maxTHRESHOLD);

        if (randomNr < springTHRESHOLD) {
            return;
        } else if (randomNr >= springTHRESHOLD && randomNr < tramolineTHRESHOLD) {
            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            int springXLoc = (int) (rand.nextFloat() * platform.getWidth());
            IGameObject powerup = powerupFactory.createSpring(0, 0);

            int xPos = (int) platform.getXPos() + springXLoc;
            if (xPos > platform.getXPos() + platform.getWidth() - powerup.getWidth()) {
                xPos = xPos - powerup.getWidth();
            }
            powerup.setXPos(xPos);
            powerup.setYPos((int) platform.getYPos() - platform.getHeight() + itemYoffset);

            content.add(powerup);
        } else if (randomNr >= tramolineTHRESHOLD) {

            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            IGameObject powerup = powerupFactory.createTrampoline((int) platform.getXPos() + trampolineXoffset,
                    (int) platform.getYPos() - platform.getHeight() + itemYoffset);
            content.add(powerup);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cleanUpPlatforms() {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : content) {
            if (e.getYPos() + Game.HEIGHT * cleanupOFFSET > Game.HEIGHT) {
                toRemove.add(e);
            }
        }

        for (IGameObject e : toRemove) {
            content.remove(e);
        }
    }

    /**
     * Creates and places platforms in the block.
     *
     * @param lastObject The highest platform in the previous block.
     */
    private void createAndPlaceObjects(final IGameObject lastObject) {
        placePlatforms(lastObject);
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
