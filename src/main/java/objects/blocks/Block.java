package objects.blocks;

import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import objects.powerups.IPowerupFactory;
import scenes.World;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import objects.IGameObject;

/* package */ class Block extends AGameObject implements IBlock {

    private static IServiceLocator serviceLocator;
    private ArrayList<IGameObject> content = new ArrayList<>();

    /* package */ Block(IServiceLocator serviceLocator, IGameObject lastObject) {
        Block.serviceLocator = serviceLocator;

        //This is only to be sure it has a certain height, after this it will be
        //dynamic to the last element added to the list
        setYPos(lastObject.getYPos() + 800);
        createAndPlaceObjects(lastObject);
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        for (IGameObject e : content) {
            e.render();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void animate() { }

    /** {@inheritDoc} */
    @Override
    public void move() { }

    /** {@inheritDoc} */
    @Override
    public void update() { }

    /** {@inheritDoc} */
    @Override
    public void addYPos(double y) {
        double current = this.getYPos();
        this.setYPos(current + y);

        for (IGameObject e : content){
            e.addYPos(y);
        }
    }

    /** {@inheritDoc} */
    @Override
    public ArrayList<IGameObject> getContent() {
        return this.content;
    }

    /** {@inheritDoc} */
    @Override
    public void placePlatforms(IGameObject lastObject) {
        int max = (Game.WIDTH + Game.HEIGHT) / 120;
        int min = 8;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = Game.HEIGHT/platformAmount;

        double t = World.vSpeedLimit/World.gravityAcceleration;

        int maxY = (int) (0.5 * World.gravityAcceleration * Math.pow(t,2));

        for (int i = 0; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = rand.nextFloat();

            int yLast = (int) lastObject.getYPos();
            int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));


            if(yLoc < yLast - maxY){
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

    /** Takes a random number between 0 and 10000 and
     * gives the platform a powerup if it's a certain number
     * between 0 and 10000.
     * @param platform The platform a powerup potentially is placed on.
     **/
    private void chanceForPowerup(IPlatform platform) {
        Random rand = new Random();

        int randomNr = (int) (rand.nextFloat() * 10000);

        if (randomNr >= 9500 && randomNr < 9900) {
            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            int springXLoc = (int) (rand.nextFloat() * platform.getWidth());
            IGameObject powerup = powerupFactory.createSpring(0, 0);

            int xPos = (int) platform.getXPos() + springXLoc;
            if (xPos > platform.getXPos() + platform.getWidth() - powerup.getWidth()) {
                xPos = xPos - powerup.getWidth();
            }
            powerup.setXPos(xPos);
            powerup.setYPos((int) platform.getYPos() - platform.getHeight() + 5);

            content.add(powerup);
        }
        else if (randomNr >= 9900) {
            IPowerupFactory powerupFactory = serviceLocator.getPowerupFactory();
            IGameObject powerup = powerupFactory.createTrampoline((int) platform.getXPos() + 20,
                    (int) platform.getYPos() - platform.getHeight() + 5);
            content.add(powerup);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void cleanUpPlatforms() {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : content) {
            if (e.getYPos() + Game.HEIGHT * 0.01 > Game.HEIGHT) {
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
    private void createAndPlaceObjects(IGameObject lastObject) {
        placePlatforms(lastObject);
    }

    /**
     * Checks if the platform collides with any of the platforms
     * in this Block. When there is a collision, delete the platform
     * from the list.
     * @param platform The IPlatform that has to be checked for collision.
     */
    private void platformCollideCheck(IPlatform platform) {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : content){
            if (serviceLocator.getCollisions().collide(platform, e)){
                toRemove.add(e);
            }
        }

        for (IGameObject e : toRemove) {
            content.remove(e);
        }
    }

}
