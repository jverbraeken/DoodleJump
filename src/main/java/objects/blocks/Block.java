package objects.blocks;

import objects.AGameObject;
import objects.blocks.platform.IPlatform;
import objects.blocks.platform.IPlatformFactory;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.Random;
import objects.IGameObject;

public class Block extends AGameObject implements IBlock {

    private static IServiceLocator serviceLocator;

    private int blockNumber;
    private ArrayList<IGameObject> content = new ArrayList<>();

    /* package */ Block(IServiceLocator serviceLocator, int blockNumber) {
        Block.serviceLocator = serviceLocator;

        this.blockNumber = blockNumber;

        createAndPlaceObjects();
    }

    private void createAndPlaceObjects() {
        placePlatforms();
    }

    private void placePlatforms() {
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
            IPlatform platform = platformFactory.newPlatform(xLoc, yLoc);
            content.add(platform);
        }
    }

    public ArrayList<IGameObject> getContent() {
        return this.content;
    }

    @Override
    public void paint() {
        for(IGameObject e : content){
            e.paint();
        }
    }

    //TODO: change to support correct implementation
    public void animate() { }

    //TODO: change to support correct implementation
    public void move() { }

    //TODO: change to support correct implementation
    public void update() { }

}
