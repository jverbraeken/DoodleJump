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
import java.util.Set;

public class StartBlock extends AGameObject implements IBlock {

    private static IServiceLocator serviceLocator;

    private Set<IGameObject> content = new HashSet<>();
    private int blockNumber;

    /* package */ StartBlock(IServiceLocator serviceLocator) {
        StartBlock.serviceLocator = serviceLocator;

        this.blockNumber = 0;
        setYPos(0);

        createAndPlaceObjects();
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        for(IGameObject e : content){
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
    public Set<IGameObject> getContent() {
        return this.content;
    }

    /** {@inheritDoc} */
    @Override
    public void placePlatforms(IGameObject lastObject) {
        int max = (int)(Game.WIDTH + Game.HEIGHT)/130;
        int min = 6;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = (int) Game.HEIGHT/platformAmount;

        IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
        IPlatform platform = platformFactory.createPlatform(Game.WIDTH/2, (int) (Game.HEIGHT/1.2));
        lastObject = platform;
        content.add(platform);

        double t = World.vSpeedLimit/World.gravityAcceleration;

        int maxY = (int) (0.5 * World.gravityAcceleration * Math.pow(t,2));

        for (int i = 1; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);

            int yLast = (int) lastObject.getYPos();

            int yLoc = (int) (yLast - heightDividedPlatforms - (heightDeviation * heightDividedPlatforms));

            if(yLoc < yLast - maxY){
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

    /** {@inheritDoc} */
    @Override
    public void addYPos(double y) {
        double current = this.getYPos();
        this.setYPos(current + y);

        for(IGameObject e : content){
            e.addYPos(y);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void cleanUpPlatforms() {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for(IGameObject e : content) {
            //A marge of 50 is used
            if(e.getYPos() + Game.HEIGHT * 0.01 > Game.HEIGHT) {
                toRemove.add(e);
            }

        }
        for(IGameObject e : toRemove) {
            content.remove(e);
        }
    }

    /**
     * Creates and places platforms in the block.
     *
     */
    private void createAndPlaceObjects() {
        placePlatforms(null);
    }

    /**
     * Checks if the platform collides with any of the platforms
     * in this Block. When there is a collision, delete the platform
     * from the list.
     * @param platform The IPlatform that has to be checked for collision.
     */
    private void platformCollideCheck(IPlatform platform) {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for(IGameObject e : content){
            if(serviceLocator.getCollisions().collide(platform, e)){
                toRemove.add(e);
            }
        }

        for(IGameObject e : toRemove) {
            content.remove(e);
        }
    }

}
