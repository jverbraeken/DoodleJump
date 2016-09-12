package objects.blocks;

import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import objects.IGameObject;

public class Block extends AGameObject implements IBlock {

    private static IServiceLocator serviceLocator;

    private HashSet<IGameObject> content = new HashSet<>();
    private int blockNumber;

    /**
     * Create and initialize a Block.
     * @param serviceLocator the ServiceLocator of this session.
     * @param blockNumber the BlockNumber of the list of Blocks in World.
     */
    /* package */ Block(IServiceLocator serviceLocator, int blockNumber) {
        Block.serviceLocator = serviceLocator;

        this.blockNumber = blockNumber;

        createAndPlaceObjects();
    }

    /** {@inheritDoc} */
    @Override
    public void paint() {
        for(IGameObject e : content){
            e.paint();
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


    private void createAndPlaceObjects() {
        placePlatforms();
    }

    public HashSet<IGameObject> getContent() {
        return this.content;
    }

    /** {@inheritDoc} */
    @Override
    public void placePlatforms() {
        int max = (int)(Game.WIDTH + Game.HEIGHT)/130;
        int min = 6;
        Random rand = new Random();
        int platformAmount = rand.nextInt((max - min) + 1) + min;
        int heightDividedPlatforms = (int) Game.HEIGHT/platformAmount;

        for (int i = 0; i < platformAmount; i++) {
            float heightDeviation = (float) (rand.nextFloat() * 1.7 - 0.8);
            float widthDeviation = (float) (rand.nextFloat() * 0.8 + 0.1);
            int yLoc;

            if(blockNumber == 0) {
                yLoc = (int) (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms));
            } else {
                yLoc = (int) (-Game.HEIGHT * (blockNumber -1) - (heightDividedPlatforms * i + (heightDeviation * heightDividedPlatforms)));
            }

            int xLoc = (int) (widthDeviation * Game.WIDTH);
            IPlatformFactory platformFactory = serviceLocator.getPlatformFactory();
            IPlatform platform = platformFactory.createPlatform(xLoc, yLoc);
            content.add(platform);
        }
    }

}
